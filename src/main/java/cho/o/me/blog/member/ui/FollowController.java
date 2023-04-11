package cho.o.me.blog.member.ui;

import cho.o.me.blog.follow.application.FollowService;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowController {

    FollowService followService;

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
