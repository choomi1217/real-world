package cho.o.me.blog.profile.ui;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.profile.application.ProfileService;
import cho.o.me.blog.profile.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    ProfileService  profileService;
    FollowService followService;
    @GetMapping(path = "/api/profiles/{username}")
    public ResponseEntity<ProfileResponse> profile(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String username){
        if(userDetails != null){
            return ResponseEntity.ok(profileService.profile(userDetails.getUsername(), username));
        }else{
            return ResponseEntity.ok(profileService.profile(username));
        }
    }

    @PostMapping(path = "/api/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> follow(
              @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String username){
        return ResponseEntity.ok(followService.follow(userDetails.getUsername(), username));
    }

    @DeleteMapping(path = "/api/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> unFollow(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String username){
        return ResponseEntity.ok(followService.unfollow(userDetails.getUsername(), username));
    }

}
