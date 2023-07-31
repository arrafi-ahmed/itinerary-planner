<script setup>
import {useStore} from "vuex";
import router from "@/router";

const store = useStore();
store.dispatch("customer/logout");
router.push({name: "drawer.login"});
</script>

<style scoped></style>
