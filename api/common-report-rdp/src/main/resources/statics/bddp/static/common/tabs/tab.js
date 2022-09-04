(function () {
  var that;
  class Tab {
    constructor(elem) {
      // 获取元素
      that = this;
      this.main = elem;
      this.add = this.main.querySelector(".tabadd");
      // li的父元素
      this.ul = this.main.querySelector(".fisrstnav ul:first-child");
      // section 父元素
      this.fsection = this.main.querySelector(".tabscon");
      this.init();
    }
    init() {
      this.updateNode();
      // init 初始化操作让相关的元素绑定事件
      this.add.onclick = this.addTab;
      for (var i = 0; i < this.lis.length; i++) {
        this.lis[i].index = i;
        this.lis[i].onclick = this.toggleTab;
        this.remove[i].onclick = this.removeTab;
        this.spans[i].ondblclick = this.editTab;
        this.sections[i].ondblclick = this.editTab;
      }
    }
    // 因为我们动态添加元素 需要从新获取对应的元素
    updateNode() {
      this.lis = this.main.querySelectorAll("li");
      this.sections = this.main.querySelectorAll("section");
      this.remove = this.main.querySelectorAll(".icon-guanbi");
      this.spans = this.main.querySelectorAll(".fisrstnav li span:first-child");
    }
    // 1. 切换功能
    toggleTab() {
      // console.log(this.index);
      that.clearClass();
      this.className = "liactive";
      that.sections[this.index].className = "conactive";
    }
    // 清除所有li 和section 的类
    clearClass() {
      for (var i = 0; i < this.lis.length; i++) {
        this.lis[i].className = "";
        this.sections[i].className = "";
      }
    }
    // 2. 添加功能
    addTab() {
      that.clearClass();
      // (1) 创建li元素和section元素
      var random = Math.random();
      var li =
        '<li class="liactive"><span>双击改名</span><span class="iconfont icon-guanbi"></span></li>';
      var section =
        '<section class="conactive">双击改内容 ' + random + "</section>";
      // (2) 把这两个元素追加到对应的父元素里面
      that.ul.insertAdjacentHTML("beforeend", li);
      that.fsection.insertAdjacentHTML("beforeend", section);
      that.init();
    }
    // 3. 删除功能
    removeTab(e) {
      e.stopPropagation(); // 阻止冒泡 防止触发li 的切换点击事件
      var index = this.parentNode.index;
      // 根据索引号删除对应的li 和section   remove()方法可以直接删除指定的元素
      that.lis[index].remove();
      that.sections[index].remove();
      that.init();
      // 当我们删除的不是选中状态的li 的时候,原来的选中状态li保持不变
      if (document.querySelector(".liactive")) return;
      // 当我们删除了选中状态的这个li 的时候, 让它的前一个li 处于选定状态
      index--;
      if (index == -1) {
        //解决当选定第一个选项卡时删除后失去选中状态
        index++;
      }
      // 手动调用我们的点击事件  不需要鼠标触发
      that.lis[index] && that.lis[index].click();
    }
    // 4. 修改功能
    editTab() {
      var str = this.innerHTML;
      this.innerHTML = '<input type="text" />';
      var input = this.children[0];
      input.value = str;
      input.select(); // 文本框里面的文字处于选定状态
      // 当我们离开文本框就把文本框里面的值给span
      input.onblur = function () {
        if (this.value != "")
          //解决选项卡内容为空之后不能再改的问题
          this.parentNode.innerHTML = this.value;
        else this.parentNode.innerHTML = "内容不能为空";
      };
      // 按下回车也可以把文本框里面的值给span
      input.onkeyup = function (e) {
        if (e.keyCode === 13) {
          // 手动调用表单失去焦点事件  不需要鼠标离开操作
          this.blur();
        }
      };
    }
  }
  this.Tab = Tab;
})();

