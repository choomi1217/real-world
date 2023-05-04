package cho.o.me.blog.member.ui;

import cho.o.me.blog.exception.NotFollowingException;
import cho.o.me.blog.member.application.MemberFollowService;
import cho.o.me.blog.member.application.MemberProfileService;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberFollowService memberFollowService;
    private final MemberProfileService memberProfileService;

    @PostMapping(path = "/api/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> follow(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String username){
        return ResponseEntity.ok(memberFollowService.follow(userDetails.getUsername(), username));
    }

    @DeleteMapping(path = "/api/profiles/{username}/follow")
    public ResponseEntity<ProfileResponse> unFollow(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String username) throws NotFollowingException {
        return ResponseEntity.ok(memberFollowService.unfollow(userDetails.getUsername(), username));
    }
    @GetMapping(path = "/api/profiles/{username}")
    public ResponseEntity<ProfileResponse> profile(
            @AuthenticationPrincipal(expression = "isAuthenticated() ? principal : null") UserDetails userDetails
            , @PathVariable String username){
        if(userDetails != null){
            return ResponseEntity.ok(memberProfileService.profile(userDetails.getUsername(), username));
        }else{
            return ResponseEntity.ok(memberProfileService.profile(username));
        }
    }

}
