# PetHelperProJect
----------------------------------------------
## 프로젝트 계획

>반려견, 반려묘에 관한 관심도 증대가 되어가고 1인 가구가 늘어감에따라  
>반려동물을 키우다가도 버려지는 동물들이 매우 많다는 점을 생각하여 해당 유기동물에 관한 정보를 쉽게 얻을수  
>있도록 접근 할 수있는 정보를 제공하고, 게시판을 통한 교류가 가능한 프로젝트를 만들고 싶었습니다.  
>공공데이터 포탈에서 제공하는 유기동물 데이터를 활용하여 제작하였습니다.  
>제작하고 기능구현까지 하게된 김에 배포까지 욕심이 나서 aws서비스를 이용하여 수동으로  
>배포를 진행한 프로젝트입니다.


---------------------------------------------
## 해당 프로젝트 배포된 주소
### www.hellopet.tk 

>AWS에서 제공하는 EC2를 활용하여 해당 프로젝트를 배포해보았습니다.  
>AWS와 freenom에서 제공하는 무료 도메인을 연결하여서 배포했습니다.
>AWS의 RDS서비스에서 제공하는 mysql서버를 연결하여 EC2내부에서 Docker이미지로 만든 mysql로 접근하는게 아닌 별개로 존재하는 db서버로 접근하는 방식으로 구현했습니다.  

------------------------------------------------------------------------------------------------------------------
## JWT토큰을 이용해서 React(View),Spring boot(Server)를 구성.

>* create-react-app 이용해서 react 기본 구성 생성.  
>* spring boot start를 이용해서 server 기본 구성 생성.  
>* spring security를 설정하여 권한이 필요한 접근에 대해서는 권한처리 완료.    
>* Security에 내장되어있는 Bcrypt password encode를 사용하여 비밀번호 암호화 했습니다.
>* JWT토큰 생성 및 로그인때 확인하는 부분을 Filter 로 처리했습니다.

>> View 단의 기본 구성 -> Bootstrap을 이용해서 UI구성
>> react router를 활용하여 페이지별 다른내용을 보여주도록 구성되어있습니다.

>> [메인]
>> ![메인페이지](https://user-images.githubusercontent.com/68931285/109326206-34bc8980-789a-11eb-934d-63d2dabd18ee.png)
>> [로그인]
>> ![로그인](https://user-images.githubusercontent.com/68931285/109326202-32f2c600-789a-11eb-8a35-bbfc620fd60b.png)
>> [회원가입]
>> ![회원가입](https://user-images.githubusercontent.com/68931285/109326218-38501080-789a-11eb-9181-41a29dea993d.png)
>> [게시판]
>> ![게시판 및 페이징](https://user-images.githubusercontent.com/68931285/109326235-40a84b80-789a-11eb-84f1-df6cda36ae13.png)


-------------------------------------------------------

## 기본 화면구성

1.게시판 상세보기의 경우 회원정보를 불러와서 수정,삭제를 보여줘야 하는부분이 있음  
회원만 접근가능하도록 JWT토큰을 체크하였습니다.  

### 게시판 상세페이지
> ![게시판 상세보기](https://user-images.githubusercontent.com/68931285/109327005-3175cd80-789b-11eb-8bcb-0686919bd251.png)

2. 해당 게시글에 대한 댓글을 작성가능.  
댓글 수정의 경우 모달창을 하나 띄워서 해당 수정된 경우를 바로 랜더링하여 보여주는방식으로 구현.  
5개씩 보여주고 그 이상의 경우 5개씩 더 불러와서 해당페이지에 새로 랜더링 하는방식으로 구현했습니다.  

### 댓글, 댓글 더보기, 댓글수정 모달창

> ![댓글수정 모달창](https://user-images.githubusercontent.com/68931285/109327028-36d31800-789b-11eb-8e1e-3c0539005197.png)
> ![댓글창 및 댓글더보기](https://user-images.githubusercontent.com/68931285/109327033-376bae80-789b-11eb-8cd0-e5dd618a8d70.png)

3.Spring boot에 내장되어있는 Resttemplate를 사용하여 공공데이터 포털에 존재하는 유기동물 정보를 불러와서 데이터를 뿌려주는 부분 구현.
### 날짜를 기준 검색창, Card형식의 유기동물 이미지 및 내용 UI.

>![유기동물 검색창](https://user-images.githubusercontent.com/68931285/109327034-38044500-789b-11eb-977f-9cd8a703eace.png)
>![유기동물이미지](https://user-images.githubusercontent.com/68931285/109327037-389cdb80-789b-11eb-8af6-b740207c7117.png)
>![유기동물 설명](https://user-images.githubusercontent.com/68931285/109327035-38044500-789b-11eb-8fb8-c0fddeb752f0.png)


------------------------------------------------------------

#### 이후 수정 방향으로 생각하는 부분들

* 가입시 이메일로 임의의 토큰값을 발송하여 인증된 회원만 이용 할 수 있게 하는 기능. 
* Oauth2 라이브러리 사용하여 oauth로그인 지원.
* 현재는 .war파일을 ec2 우분투 서버에서 백그라운드로 실행, react 서버를 yarn으로 실행해서 띄우는걸 직접 했으나  
배치파일이나 다른방법을 찾아서 자동화 할까 생각중입니다.  

-------------------------------------------------------------

#### 트러블 슈팅 관련사항들

* Spring boot에서 서버파일을 배포했더니 프리티어를 사용하기때문에 메모리 부족을 발생시킴

swap을 이용하여 해결  

    $ sudo fallocate -l 2G /swapfile
    $ sudo chmod 600 /swapfile
    $ sudo mkswap /swapfile
    $ sudo swapon /swapfile

    절차가 성공했는지 확인.  
    $ sudo swapon -s
    
    편집기로 파일열기.  
    sudo vi /etc/fstab
    sudo nano /etc/fstab
    
    해당부분 맽끝에 저장
    /swapfile swap swap defaults 0 0
    
* EC2 우분투 환경에서 apt로 yarn,npm,nodejs 등등 설치시 너무 이전버전으로 설치되는 문제

apt에 최신버전으로 다운가능한 주소를 추가하여서 높은버전이 다운로드 가능하도록 함.

    yarn 설치위한 주소 apt에 추가

    curl -sS https://dl.yarnpkg.com/debian/pubkey.gpg | sudo apt-key add -
    echo "deb https://dl.yarnpkg.com/debian/ stable main" | sudo tee /etc/apt/sources.list.d/yarn.list
    
    yarn 설치
    sudo apt update && sudo apt install yarn
 
 
