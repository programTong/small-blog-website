<template>
  <div>
    <table>
      <thead>
      <tr>
        <th>标题</th>
        <th>内容</th>
        <th>标签</th>
        <th>分类</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(article,i) in list" @click="toReadArticle(article)">
        <td>{{article.title}}</td>
        <td>{{article.content===null?"":article.content.substr(0,article.content.length>20?20:article.content.length)}}</td>
        <td>{{article.tags}}</td>
        <td>{{article.categories}}</td>
      </tr>
      </tbody>
    </table>
    <div>
      <input type="button" value="上一页" @click="getArticlePage(1)">
      <span>{{pageNum+'/'+pages}}</span>
      <input type="button" value="下一页" @click="getArticlePage(2)">
    </div>
  </div>
</template>

<script>
import GlobalVar from "../js/GlobalVar";

export default {
  name: "Latest_Article",
  data(){
    return {
      req_page: 1,
      req_page_size: 1,
      total: 3,
      pageNum: 1,
      pageSize: 2,
      size: 2,
      pages: 2,
      prePage: 0,
      nextPage: 2,
      isFirstPage: {},
      isLastPage: {},
      hasPreviousPage: {},
      hasNextPage: {},
      navigatePages: 8,
      firstPage: 1,
      lastPage: 2,
      list: {}
    }
  },
  methods: {
    toReadArticle: function (article){
      this.$router.replace({
        path: '/blog_sea/article_view',
        query: {
          article: article
        }
      })
    },
    getArticlePage: function (i){
      if (i===1){
        if (this.isFirstPage!==true){
          this.loadData(this.pageNum-1,this.req_page_size);
        }
      }else if (i===2){
        if (this.isLastPage!==true){
          this.loadData(this.pageNum+1,this.req_page_size);
        }
      }
    },
    loadData: function (req_page,req_page_size){
      fetch('http://localhost:8080/article/latest?pageNum='+req_page+'&pageSize='+req_page_size,{
        method: 'get',
        headers: {
          "Authorization": GlobalVar.user_auth_data.role+" "+GlobalVar.user_auth_data.token
        },
        mode: 'cors'
      }).then(response => {
        return response.json()
      }).then(json => {
        console.log(json);
        if (json.flag === true){
          let data = json.data;
          this.total= data.total;
          this.pageNum= data.pageNum;
          this.pageSize= data.pageSize;
          this.size= data.size;
          this.pages= data.pages;
          this.prePage= data.prePage;
          this.nextPage= data.nextPage;
          this.isFirstPage= data.isFirstPage;
          this.isLastPage= data.isLastPage;
          this.hasPreviousPage= data.hasPreviousPage;
          this.hasNextPage= data.hasNextPage;
          this.navigatePages= data.navigatePages;
          this.firstPage= data.firstPage;
          this.lastPage= data.lastPage;
          this.list=data.list
        }else{
          alert("最新文章加载失败");
        }
      }).catch(e => {
        console.log(e);
      })
    }
  },
  beforeRouteEnter(to,from,next){
    next(vm => {
      vm.loadData(vm.req_page,vm.req_page_size);
    })
  }
}
</script>

<style scoped>

</style>
