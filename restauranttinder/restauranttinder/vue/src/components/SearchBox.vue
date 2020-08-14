<template>
<div class="zip-search-box">
    <span class="zip-search-box">
    <input type="text" id="thing" v-model="location.zip" placeholder="Search any zip code"/>
    <label for="zip-search" class="sr-only"></label>
    <button @click="searchZip" id="submitbtn" class="submit-btn">Submit</button>
    </span>
</div>
</template>

<script>
import DBService from '@/services/DBService';

export default {
    name: "search-box",

    data() {
        return {
            location: {
                zip: '',
                lat: '',
                lng: ''
            }
        }
    },

    methods: {
        searchZip() {
            if (this.location.zip.length >= 5){
            DBService.searchRandomZip(this.location.zip).then(response => {
                this.location = response.data;
                this.$store.state.user.lat = this.location.lat;
                this.$store.state.user.lng = this.location.lng;

            });
            }
            }
    }
}

</script>
<style>
.zip-search-box {
    display: flex;
    flex-direction: row;
    margin-top: .25rem;
    margin-bottom: .5rem;
    width: auto;
}

.zip-search-box input {
    padding: 5px;
    border-radius: 15px;
    margin-right: 5px;
    margin-top: 5px;
    width: 46.5vw;
}

@media (min-width: 700px) { 
    .zip-search-box {
    display: flex;
    flex-direction: row;
    margin-top: .25rem;
    margin-bottom: .5rem;
    width: auto;
    margin-left: 7.55%;
}

.zip-search-box input {
    padding: 5px;
    border-radius: 15px;
    margin-right: 5px;
    margin-top: 5px;
    width: 21.4vw;
    max-width: 197.09px;
}
@media (min-width: 928px) {
.zip-search-box {
  justify-content: center;
  margin-left: 58px;
}
.zip-search-box input {
    max-width: 220.5px;
}
}

}
</style>