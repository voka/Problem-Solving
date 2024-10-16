// 의존 역전 원칙 : 고수준의 모듈은 저수준 모듈에 의존해서는 안된다.
// 두 모듈 모두 추상화에 의존해야 하며, 추상화는 세부 사항에 의존해서는 안된다.
// 고수준 모듈(비지니스 로직)은 저수준(구현 세부 사항)보다 더 높은 수준의 추상화에 의존해야 한다.
// 이렇게 하면 시스템 유연성을 높이고 변경에 대한 저항력을 강화할 수 있다.

/**
 * UserService는 EmailService에 직접적으로 의존하고 있다.
 * 이를 해결하기 위해서는 추상화된 인터페이스에 고수준의 모듈이 의존하게 변경해야함.
 * 이렇게 하면 고수준 모듈과 저수준 모듈 간의 의존성을 줄일 수 있다. 
 * 결국 이렇게 하는 이유는 고수준 모듈과 저수준 모듈간의 의존성을 줄여 유연성과 재사용성을 높이고, 유지 보수가 용이하게 만들기 위함이다.
 */



class EmailService {
    public void sendEmail(String message) {
        // 이메일 전송 로직
        System.out.println("Sending email: " + message);
    }
}


class UserService {
    private EmailService emailService = new EmailService(); // 직접 의존

    public void registerUser(String username) {
        // 사용자 등록 로직
        emailService.sendEmail("User " + username + " registered.");
    }
}