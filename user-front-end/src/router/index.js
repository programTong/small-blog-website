import VueRouter from 'vue-router'
import Vue from 'vue'
import Status from '../components/Status.vue'
import Article_Publish from '../components/Article_Publish.vue'
import Article_Manage from '../components/Article_Manage.vue'
import Category_Manage from '../components/Category_Manage.vue'
import Article_Modify from '../components/Article_Modify.vue'
import Latest_Article from '../components/Latest_Article.vue'
import Blog_Sea from '../components/Blog_Sea.vue'
import Search_Article from '../components/Search_Article.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Article_View from '../components/Article_View.vue'
import Comment from '../components/Comment.vue'
import UserView from '../components/UserView.vue'
import App_User from '../components/App_User.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '',
        components: {
            body_router_view: Login,
        }
    },
    {
        path: '/user_view',
        components: {
            body_router_view: UserView
        }
    },
    {
        path: '/login',
        components: {
            body_router_view: Login
        }
    },
    {
        path: '/register',
        components: {
            body_router_view: Register
        }
    },
    {
        path: '/status',
        components: {
            body_router_view: Status,
        }
    },
    {
        path: '/article_publish',
        components: {
            body_router_view: Article_Publish
        }
    },
    {
        path: '/article_manage',
        components: {
            body_router_view: Article_Manage
        }
    },
    {
        path: '/category_manage',
        components: {
            body_router_view: Category_Manage
        }
    },
    {
        path: '/article_modify',
        components: {
            body_router_view: Article_Modify
        }
    },
    {
        path: '/blog_sea',
        components: {
            body_router_view: Blog_Sea
        },
        children: [
            {
                path: '',
                components: {
                    sea_rv: Latest_Article
                }
            },
            {
                path: '/blog_sea/latest_article',
                components: {
                    sea_rv: Latest_Article
                }
            },
            {
                path: '/blog_sea/search_article',
                components: {
                    sea_rv: Search_Article
                }
            },
            {
                path: '/blog_sea/article_view',
                components: {
                    sea_rv: Article_View
                },
                children: [
                    {
                        path: '/blog_sea/article_view/comment',
                        components: {
                            article_comment: Comment
                        }
                    },
                ]
            },
        ]
    },
]

for (let i = 0; i < routes.length; i++) {
    let route = routes[i];
    route.components.app_user=App_User;
}

const router = new VueRouter({
    routes,
    mode: 'history'
})

export default router
