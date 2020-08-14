<template>
 <div class="update-profile">
  <div class="background-image"></div>
  <div class="content-zip">
   <h2>Please enter your new zip-code</h2>
   <form class="change-zip" @submit.prevent="changeZip">
    <label for="zip-code" class="sr-only"></label>
    <input type="text" id="zip-code" v-model="user.zip"/>
    <button type="submit" id="submitbtn" class="submit-btn">Submit</button>
   </form>
  </div>
 </div>
</template>

<script>
import authService from '@/services/AuthService'

export default {
  name: "update-profile",
  data() {
    return {
      user: {
        zip: ''
      }
    }
},
  methods: {
    changeZip() {                       
      authService.changeZipCode(this.$store.state.user.id, this.user).then(response => {
      if (response.status === 204) {
      this.$router.push('/');
            }
        })
      }
    },
  }        
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Cookie&family=Montserrat&display=swap');

.update-profile {
    display: flex;
    flex-direction: column;
    background-image: url('../assets/background10.jpg');
    min-height: 100vh;
    min-width: 100vw;
    box-sizing: border-box;
    background-repeat: no-repeat;
    background-size: cover;
    justify-content: center;
    align-items: center;
}
.content-zip {
    background-color: rgb(0, 0, 0, 0.9);  
    height: 45vh;
    width: auto;
    border: 1px solid black; 
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;    
    margin-top: -25%;   
    border-radius: 15px;
}
.content-zip h2 {
    padding: 2rem;
    color: white;
    font-family: 'Cookie', cursive;
}
  
.change-zip {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 50vw;
}
.submit-btn {
  background: #222;
  height: 3rem;
  min-width: 12%;
  border: none;
  border-radius: 10px;
  color: #eee;
  font-size: 30px;
  font-family: 'Cookie', cursive;
  position: relative;
  transition: 2s;
  -webkit-tap-highlight-color: transparent;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding-top: 5px;
  margin-top: 10px;
}

#submitbtn:hover {
  background: transparent;
}

/* .submit-button {
  font-size: 100%;
  font-family: inherit;
  border: 0;
  padding: 0;  
  background: transparent;
  color: #eee; */


</style>