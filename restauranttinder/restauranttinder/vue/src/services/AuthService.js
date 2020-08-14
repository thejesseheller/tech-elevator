import axios from 'axios';

const http = axios.create({
  baseURL: "http://localhost:8080/",
  headers: {    
    'content-type': "application/json"
  }
});

export default {

  login(user) {
    return http.post('/login', user)
  },

  register(user) {
    return http.post('/register', user)
  },

  changeZipCode(userId, user) {
    return http.put(`/update/${userId}`, {
      zip: user.zip
    });
  }

}
