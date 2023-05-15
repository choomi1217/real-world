개인적으로 게시글을 구현하면서 느낀점이 있다면
내가 너무 단순하게 개발을 하고 있단 생각을 했습니다.

기존에 저는 myBatis를 사용했고 
도메인에 대한 신경은 최대한 끄고 myBatis 하듯이 mvc 패턴을 그대로 jpa를 쓰려고 보니 많은 문제가 있었으나 애써 무시했었습니다만

태그와 게시글을 조인해야하는 일이 있었는데

게시글과 태크를 조인할때 태그의 uuid 를 가지고 조인을 하려고 했는데

1. 처음 요청이 들어오면 그중에 태그는 **List<String>** 의 형태로 들어옴
```java
public class ArticleRequest {

    String title;
    String description;
    String body;
    List<String> tagList;

}
```

2. 태그이름들로 검색후 있으면 그대로 돌려주고 없으면 create 과정을 거쳐서 Tag의 uuid를 돌려줌
3. article에 저장된 tag들이 uuid 라서 다시 검색하는 과정이 필요해져버림
4. 왜냐하면 아래처럼 나와야 하기 때문..
```json
"tagList": ["dragons", "training"]
```


