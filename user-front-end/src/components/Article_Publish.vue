<template>
  <div>
    <div>
      <span>标题</span><input type="text" v-model="title">
    </div>
    <div>
      <span>标签</span>
      <span v-for="label in labels" style="background-color: dodgerblue; margin: 0px 2px">{{label}}</span>
      <input type="text" @keyup.enter="label_enter" v-model="label_input">
    </div>
    <div>
      <div>
        <span>可选分类</span>
        <span v-for="category in categories" @click="select_category(category)" style="margin: 0px 3px;border: 1px solid black">{{category.name}}</span>
      </div>
      <span>分类</span>
      <span v-for="category in selected_categories" style="background-color: dodgerblue; margin: 0px 2px">{{category.name}}</span>
    </div>
    <div>
      <input type="text" id="text_input_owernwox" v-model="content">
    </div>
    <div>
      <input type="button" value="保存" @click="save">
    </div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";

export default {
  name: "Article_Publish",
  data(){
    return {
      labels: [],
      categories: [],
      title: "",
      content: "",
      label_input: "",
      selected_categories: []
    }
  },
  methods: {
    select_category: function (category){
      this.selected_categories.push(category);
      console.log(this.selected_categories);
    },
    save: function (){
      let names=[];
      for (let i = 0; i < this.selected_categories.length; i++) {
        names.push(this.selected_categories[i].name);
      }
      fetch('http://localhost:8080/user/article/publish',{
        method: 'post',
        mode: 'cors',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token,
          "Content-Type": "application/json;charset=UTF-8"
        },
        body: JSON.stringify({
          title: this.title,
          content: this.content,
          status: "publish",
          type: "post",
          allowComment: true,
          tags: this.labels.join(','),
          categories: names.join(','),
        })
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          alert("保存成功");
        }else{
          alert("保存失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    loadData: function (){
      fetch('http://localhost:8080/user/category',{
        method: 'get',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        mode: 'cors'
      }).then(response => {
        return response.json()
      }).then(json => {
        if (json.flag === true){
          this.categories=json.data.categories;
        }else{
          alert("分类加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    },
    label_enter: function (){
      if (this.label_input!==undefined&&this.label_input!==""){
        this.labels.push(this.label_input)
      }
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
#text_input_owernwox{
  width: 600px;
  height: 500px;
}
</style>
