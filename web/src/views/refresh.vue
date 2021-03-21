<template>
    
</template>

<script>
    export default {
        name: "Refresh",
        beforeRouteEnter(to, from, next) {
            next(vm => {
                vm.$store.commit("permission/DELETE_ROUTE_TAB",from.fullPath);
                vm.$router.replace("/system/menu")
            })
        }
    }
</script>

<style scoped>

</style>