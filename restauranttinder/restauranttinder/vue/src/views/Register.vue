<template>
 <div class="registration-container">
  <form class="form-register" @submit.prevent="register">
   <h1 id = "heading" class="register-heading">We have a table ready...</h1>
   <h2 id="subheading" class="register-heading">Register a new account below</h2>
   <div class="alert alert-danger" role="alert" v-if="registrationErrors">
    {{ registrationErrorMsg }}
   </div>
   <div id="new-user-inputs">
   <label for="username" class="sr-only">Username</label>
   <input
    type="text"
    id="username"
    class="form-control"
    placeholder="Username"
    v-model="user.username"
    required
    autofocus
   />
   <label for="password" class="sr-only">Password</label>
   <input
    type="password"
    id="password"
    class="form-control"
    placeholder="Password"
    v-model="user.password"
    required
    />
   <input
    type="password"
    id="confirmPassword"
    class="form-control"
    placeholder="Confirm Password"
    v-model="user.confirmPassword"
    required
    />
    <label for="zip_code" class="sr-only">zipcode</label>
    <input 
      type="text"
      id="zip_code"
      class="form-control"
      placeholder="zip-code"
      v-model="user.zip"
      required
     />
    <button id="btn" class="button" type="submit">
      Create Account
    </button>
    <router-link id="back-to-login" class="register-heading" :to="{ name: 'login' }">I have an account, take me back</router-link>
    </div>
  </form> 
 </div>
</template>

<script>
import authService from '../services/AuthService';
//import locationService from '../services/LocationService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'user',
        zip: '',
      },

      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    /* getLatLng(zip) {
      locationService.getLatLng(zip).then(response => {
        this.location.lat = response.data.lat;
        this.location.lng = response.data.lng;
        console.log(this.user.location.lat);
      })
    }, */
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Password & Confirm Password do not match.';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style>

@import url('https://fonts.googleapis.com/css2?family=Cookie&family=Montserrat&display=swap');

.registration-container { /*make whole page flex column??*/
    display: flex;
    justify-content: center;
    align-items: center;
    flex-direction: column;
    background-image: url(../assets/background-1.png);
    background-repeat:no-repeat;
    background-size: cover;
    min-height: 100vh;
    min-width: 100vw;
}

.form-register {
  display: flex;
  flex-direction: column;
  width: 40vw;
  height: auto;
  max-height: 100vh;
  position: absolute;  
  color: white;
  border-radius: 10px;
  background-color: rgb(0, 0, 0, 0.8);
  border: 1px solid #ffffff
}

#new-user-inputs {  
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

#username, #password, #confirmPassword, #zip_code, #btn {
  width: 60%;
  padding: 5px;
  margin: 5px;
}
.register-heading {
  font-family: 'Cookie', cursive;
  text-shadow: -1px 1px 2px #000,
	1px 1px 2px #000,
	1px -1px 0 #000,
	-1px -1px 0 #000;
}

#heading {
  font-size: 5rem;    
}
#subheading {
  font-size: 2.5rem;      
}
#back-to-login {  
  color:white;
  font-size:2rem;
}

#btn {
  background: #222;
  height: 50px;
  min-width: 150px;
  border: none;
  border-radius: 10px;
  color: #eee;
  font-size: 30px;
  font-family: 'Cookie', cursive;
  position: relative;
  transition: 1s;
  -webkit-tap-highlight-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding-top: 5px;
  margin-top: 10px;
}

#btn:hover {
  background: transparent;
}

@media (max-width: 700px) { /* this is all wrong */
.form-register {
  width: 100vw;
  margin-top: -20%;
  }

#register-header h1 {
  position: absolute;
  bottom: 5%;
  left: 50%;
  transform: translate(-50%, -50%);
  font-size: auto;

}
}

</style>
