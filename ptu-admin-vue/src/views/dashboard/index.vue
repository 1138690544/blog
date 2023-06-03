<template>
  <div class="dashboard-container">
    <div class="dashboard-text" align="center">
      PTU博客管理系统
    </div>
    <div class="box">
    <el-tag>简洁</el-tag>
    <el-tag type="success">美观</el-tag>
    <el-tag type="info">性能高</el-tag>
    <el-tag type="warning">无bug</el-tag>
    <el-rate
        v-model="value"
        disabled
        show-score
        text-color="#ff9900"  
        score-template="{value}">
    </el-rate>
    </div>
  </div>
  
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  },
  data() {
      return {
        value: 5.0,
        deadline2: Date.now() + 1000 * 60 * 60 * 8
      }
    }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.box {
    
    position:absolute;
    bottom:0;
    right:20px;

}
</style>
