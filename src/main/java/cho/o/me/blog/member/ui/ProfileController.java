package cho.o.me.blog.member.ui;

import cho.o.me.blog.member.application.MemberService;
import cho.o.me.blog.member.ui.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    MemberService memberService;
    @GetMapping(path = "/api/profiles/{username}")
    public ResponseEntity<ProfileResponse> profile(
            @AuthenticationPrincipal UserDetails userDetails
            , @PathVariable String username){
        if(userDetails != null){
            return ResponseEntity.ok(memberService.profile(userDetails.getUsername(), username));
        }else{
            return ResponseEntity.ok(memberService.profile(username));
        }
    }

}

