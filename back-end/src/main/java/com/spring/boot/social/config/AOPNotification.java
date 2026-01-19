package com.spring.boot.social.config;

import com.spring.boot.social.services.NotificationService;
import com.spring.boot.social.utils.enums.NotificationType;
import com.spring.boot.social.vm.NotificationRequestVm;
import com.spring.boot.social.vm.PostReactionAccountVm;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AOPNotification {
    private final NotificationService notificationService;

    //for book logging (add, remove, update)
    @AfterReturning(pointcut = "execution(* com.spring.boot.social.services.impl.ReactionPostServiceImpl.reactionRequest(..))", returning = "result")
    public void afterReaction(JoinPoint joinPoint, Object result) {
        PostReactionAccountVm postReactionAccountVm = (PostReactionAccountVm) result;
        //notification
        NotificationRequestVm notificationRequestVm = new NotificationRequestVm();
        notificationRequestVm.setSenderId(postReactionAccountVm.getAccount().getId());
        notificationRequestVm.setSenderName(postReactionAccountVm.getAccount().getFullName());
        notificationRequestVm.setType(NotificationType.LIKE);
        notificationRequestVm.setReferenceId(postReactionAccountVm.getPost().getId());
        notificationRequestVm.setRecipientId(postReactionAccountVm.getPost().getAccount().getId());
        notificationService.saveNotification(notificationRequestVm);
    }
}
