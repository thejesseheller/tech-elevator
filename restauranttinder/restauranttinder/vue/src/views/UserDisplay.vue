<template>
<div class="style">
  <recommendation />
</div>
</template>

<script>
import recommendation from '@/components/Recommendation';

export default {
    name: "user-display",
    components: {
        recommendation
    }
};
</script>
<style>
.style{
  background-image: url(../assets/background2.jpg);
  background-repeat:no-repeat;
  background-size: cover;
  background-position: bottom;
}
</style>