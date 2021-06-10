<template>
  <div>
    <table>
      <thead>
      <tr>
        <th>分类名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="category in categories">
        <td>{{category.name}}</td>
        <td><input type="button" value="删除" @click="deleteCategory(category)"></td>
      </tr>
      <tr>
        <td><input type="text" v-model="new_category_name"></td>
        <td><input type="button" value="添加" @click="addCategory"></td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";

export default {
  name: "Category_Manage",
  data(){
    return {
      categories: [],
      new_category_name: ""
    }
  },
  methods: {
    addCategory: function (){
      fetch('http://localhost:8080/user/category',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        body: JSON.stringify({
          cname: this.new_category_name
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          alert("添加唉分类成功");
          this.loadData();
        }else{
          alert("添加分类失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    deleteCategory: function (category){
      fetch('http://localhost:8080/user/category/delete',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        body: JSON.stringify({
          mid: category.mid
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          alert("删除分类成功");
          this.loadData();
        }else{
          alert("删除分类失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    loadData: function (){
      fetch('http://localhost:8080/user/category',{
        method: 'get',
        headers: {
          "Content-Type": "application/json;charset=UTF-8",
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        mode: 'cors'
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          this.categories=json.data.categories;
          console.log(this.categories);
        }else{
          alert("分类加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.loadData();
    })
  }
}
</script>

<style scoped>

</style>
