import axios from 'axios';


class JWTService{
    //JWT 토큰 저장되어있는지 확인 후 없으면 false를 리턴하고 로그인페이지로 보낸다
    checkLogin(JWT) {
        if (JWT === null) {
            alert('로그인 후 이용가능합니다');

            let login = '/login';
            window.location.assign(login);
            return false;
        }
    }
    
}

export default new JWTService();
