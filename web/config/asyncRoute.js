/*
   所有的路由页面都需要在views目录之下!!
 */
export const importComponent=(url)=>{
   //这里必须要包含views路径
   return require('@/views'+url+'.vue').default;
}

export const  importPage404=()=>{
  return require('@/views/404.vue').default;
}
