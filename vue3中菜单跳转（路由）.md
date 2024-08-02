# vue3中菜单跳转（路由）

在 Vue 3 中，可以使用 Vue Router 来实现根据侧边菜单的选择在 `<body>` 中显示不同页面的功能。Vue Router 是 Vue.js 官方提供的路由管理器，能够让你轻松地在单页面应用中管理和切换不同的视图。

### 步骤 1：安装 Vue Router

首先，确保你的 Vue 项目中安装了 Vue Router。如果还没有安装，可以通过以下命令进行安装：

```sh
npm install vue-router@next
```

### 步骤 2：设置 Vue Router

在 `src` 目录下创建一个新的文件 `router/index.js`，并在其中配置路由：

```javascript
// src/router/index.js
import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/HomePage.vue'
import AboutPage from '../components/AboutPage.vue'
import ContactPage from '../components/ContactPage.vue'

const routes = [
  { path: '/', component: HomePage },
  { path: '/about', component: AboutPage },
  { path: '/contact', component: ContactPage }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
```

### 步骤 3：创建页面组件

在 `src/components` 目录下创建你需要的页面组件，如 `HomePage.vue`、`AboutPage.vue` 和 `ContactPage.vue`。

**HomePage.vue**:
```vue
<template>
  <div>
    <h1>Home Page</h1>
    <p>Welcome to the Home Page!</p>
  </div>
</template>

<script>
export default {
  name: 'HomePage'
}
</script>
```

**AboutPage.vue**:
```vue
<template>
  <div>
    <h1>About Page</h1>
    <p>Welcome to the About Page!</p>
  </div>
</template>

<script>
export default {
  name: 'AboutPage'
}
</script>
```

**ContactPage.vue**:
```vue
<template>
  <div>
    <h1>Contact Page</h1>
    <p>Welcome to the Contact Page!</p>
  </div>
</template>

<script>
export default {
  name: 'ContactPage'
}
</script>
```

### 步骤 4：配置主应用

在 `src/main.js` 中配置 Vue Router：

```javascript
// src/main.js
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')
```

### 步骤 5：创建主应用模板

在 `src/App.vue` 中添加侧边菜单和路由视图：

```vue
<template>
  <div id="app">
    <nav>
      <ul>
        <li><router-link to="/">Home</router-link></li>
        <li><router-link to="/about">About</router-link></li>
        <li><router-link to="/contact">Contact</router-link></li>
      </ul>
    </nav>
    <router-view></router-view>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
/* Add your styles here */
nav ul {
  list-style-type: none;
  padding: 0;
}

nav ul li {
  display: inline;
  margin-right: 10px;
}

#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
```

### 运行项目

在项目根目录下运行以下命令启动开发服务器：

```sh
npm run serve
```

访问 `http://localhost:8080`，你应该能够看到侧边菜单，并且根据菜单的选择在 `<body>` 中显示不同的页面内容。

### 解释

1. **Vue Router 配置**：通过在 `router/index.js` 中配置路由，你可以定义不同路径对应的组件。
2. **组件创建**：在 `src/components` 目录下创建页面组件，如 `HomePage.vue`、`AboutPage.vue` 和 `ContactPage.vue`。
3. **主应用配置**：在 `main.js` 中引入并使用 Vue Router，使其成为 Vue 应用的一部分。
4. **主应用模板**：在 `App.vue` 中使用 `<router-link>` 组件创建导航菜单，并使用 `<router-view>` 组件来渲染匹配当前路径的组件。

这样，通过 Vue Router 的配置和使用，可以实现根据侧边菜单的选择在 `<body>` 中显示不同页面的功能。