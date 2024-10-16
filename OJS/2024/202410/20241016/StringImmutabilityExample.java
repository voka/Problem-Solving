public class StringImmutabilityExample {
    public static void main(String[] args) {
        // 문자열 객체 생성
        String originalString = "Hello";
        String originalString2 = "Hello";
        System.out.println("원본 문자열: " + originalString); // Hello

        // 문자열 수정 시도
        String modifiedString = originalString.concat(", World!");
        System.out.println("수정된 문자열: " + modifiedString); // Hello, World!

        // 원본 문자열이 여전히 동일한지 확인
        System.out.println("원본 문자열 (변경 후): " + originalString); // Hello

        // 문자열의 해시코드 출력
        System.out.println("원본 문자열 해시코드: " + originalString.hashCode());
        System.out.println("원본 문자열 해시코드: " + originalString2.hashCode());
        originalString2 = "Hooel";
        System.out.println("원본 문자열 해시코드: " + originalString2.hashCode());
        System.out.println("수정된 문자열 해시코드: " + modifiedString.hashCode());
    }
}