package org.springsource.examples.spring31.web;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springsource.examples.spring31.services.User;
import org.springsource.examples.spring31.services.UserService;

import javax.inject.Inject;
import java.io.InputStream;

/**
 * Simple Spring MVC controller that can be used to adminster information about the users
 *
 * @author Josh Long
 */
@Controller
public class UserApiController  implements ApplicationListener<HttpSessionCreatedEvent  > {

    /**
     * Root URL template for all modifications to a {@link User}
     */
    static public final String USER_COLLECTION_URL = "/api/users";
    public static final String USER_COLLECTION_USERNAMES_URL = USER_COLLECTION_URL + "/usernames";
    static public final String USER_COLLECTION_ENTRY_URL = USER_COLLECTION_URL + "/{userId}";
    static public final String USER_COLLECTION_ENTRY_PHOTO_URL = USER_COLLECTION_ENTRY_URL + "/photo";

    private Logger log = Logger.getLogger(getClass());
    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = USER_COLLECTION_ENTRY_URL, method = RequestMethod.GET)
    @ResponseBody
    public User getUserById(@PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId);
    }

    @RequestMapping(value = USER_COLLECTION_ENTRY_URL, method = RequestMethod.PUT)
    @ResponseBody
    public User updateUserById(@PathVariable("userId") Long userId,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("firstname") String fn,
                               @RequestParam("lastname") String ln) {
        User existingUser = this.getUserById(userId);
        return this.userService.updateUser(userId, username, password, fn, ln, existingUser.isImportedFromServiceProvider());
    }

    @RequestMapping(value = USER_COLLECTION_USERNAMES_URL, method = RequestMethod.GET)
    @ResponseBody
    public boolean isUserNameTaken(@RequestParam("username") String username) {
        boolean taken = this.userService.isUserNameAlreadyTaken(username);
        log.debug("the username " + username + " is taken? " + taken + ".");
        return taken;
    }

    @RequestMapping(value = USER_COLLECTION_URL, method = RequestMethod.POST)
    public ResponseEntity<User> registerUser(@RequestParam("username") String username,
                                             @RequestParam("password") String password,
                                             @RequestParam("firstname") String fn,
                                             @RequestParam("lastname") String ln,
                                             @RequestParam("imported") boolean importedFromServiceProvider,
                                             UriComponentsBuilder componentsBuilder) throws Throwable {

        User user = this.userService.createOrGet(username, password, fn, ln, importedFromServiceProvider);

        UriComponents uriComponents = componentsBuilder.path(USER_COLLECTION_ENTRY_URL).buildAndExpand(user.getId());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uriComponents.toUri());

        return new ResponseEntity<User>(user, httpHeaders, HttpStatus.CREATED);
    }


    @RequestMapping(value = USER_COLLECTION_ENTRY_PHOTO_URL, method = RequestMethod.POST)
    @ResponseBody
    public Long uploadBasedOnPathVariable(@PathVariable("userId") Long userId, @RequestParam("file") MultipartFile file) throws Throwable {
        byte[] bytesForImage = file.getBytes();
        userService.writeUserProfilePhotoAndQueueForConversion(userId, file.getName(), bytesForImage);
        return userId;
    }

    @RequestMapping(value = USER_COLLECTION_ENTRY_PHOTO_URL, method = RequestMethod.GET)
    public ResponseEntity<byte[]> renderMedia(@PathVariable("userId") Long userId) throws Throwable {
        InputStream is = userService.readUserProfilePhoto(userId);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_JPEG);
        byte buffer[] = IOUtils.toByteArray(is);
        return new ResponseEntity<byte[]>(buffer, httpHeaders, HttpStatus.OK);
    }

    @Override
    public void onApplicationEvent(HttpSessionCreatedEvent event) {
         System.out.println( "Event: " + ToStringBuilder.reflectionToString(  event));
    }
}
