(function ($) {
  $.fn.rdpTabs = function (opt) {
    var that;
    class Tab {
      constructor(elem,styles) {
        console.log(styles);
        // 获取元素
        that = this;
        this.main = elem;
        this.add = this.main.querySelector(".tabadd");
        // li的父元素
        this.ul = this.main.querySelector(".fisrstnav ul:first-child");
        // section 父元素
        this.fsection = this.main.querySelector(".tabscon");
        this.nav = this.main.querySelector(".fisrstnav");
        this.bgColor = styles.bgColor||"#111a25";
        this.currColor = styles.currColor||"#293c55";
        this.tabColor = styles.tabColor||"#111a25";
        this.tabFontColor = styles.tabFontColor||"#8d9ea7";
        this.init(true);
      }
      init(sts) {
        this.updateNode();
        // init 初始化操作让相关的元素绑定事件
        if (this.add){
          this.add.onclick = this.addTab;
        }
        for (var i = 0; i < this.lis.length; i++) {
          if (sts&&i==0) {
            that.clearClass();
            this.lis[i].className = "liactive";
            $(this.lis[i]).css("background-color",this.currColor);
            that.sections[i].className = "conactive";
          }else{
            $(this.lis[i]).css("background-color",this.tabColor);
          }
          $(this.lis[i]).find("span").css("color",this.tabFontColor);
          $(this.ul ).css("background-color",this.bgColor);
          this.lis[i].index = i;
          this.lis[i].onclick = this.toggleTab;
          if (this.remove[i]){
            this.remove[i].onclick = this.removeTab;
          }
          this.spans[i].ondblclick = this.editTab;
          if (this.sections[i].querySelector(".fa-plus")) {
            this.sections[i].querySelector(".fa-plus").onclick = function () {
              addSlide($(that.main), $(this));
            };
          }
          if (sts){
            if($(that.sections[i]).find(".tag-charts").length>0){
              var echartsInstance = echarts. getInstanceByDom($(that.sections[i]).find(".tag-charts")[0]);
              if (echartsInstance){
                echartsInstance.resize();
              }
            }
          }
          // this.sections[i].ondblclick = this.editTab;
        }
        this.updateHeight();
      }
      // 因为我们动态添加元素 需要从新获取对应的元素
      updateNode() {
        this.lis = this.main.querySelectorAll("li");
        this.sections = this.main.querySelectorAll("section");
        this.remove = this.main.querySelectorAll(".fa-times");
        this.spans = this.main.querySelectorAll(
          ".fisrstnav li span:first-child"
        );
      }
      updateHeight() {
        var t = this.nav.offsetHeight > 50 ? this.nav.offsetHeight : 50;
        this.fsection.style.top = t + "px";
      }
      // 1. 切换功能
      toggleTab() {
        // console.log(this.index);
        that.clearClass();
        this.className = "liactive";
        that.sections[this.index].className = "conactive";
        $(this).css("background-color",that.currColor);
        if($(that.sections[this.index]).find(".tag-charts").length>0){

          var echartsInstance = echarts. getInstanceByDom($(that.sections[this.index]).find(".tag-charts")[0]);
          if (echartsInstance){
            echartsInstance.resize();
          }
        }
      }
      // 清除所有li 和section 的类
      clearClass() {
        for (var i = 0; i < this.lis.length; i++) {
          this.lis[i].className = "";
          this.sections[i].className = "";
          $(this.lis[i]).css("background-color",this.tabColor);
        }
      }
      // 2. 添加功能
      addTab() {
        that.clearClass();
        // (1) 创建li元素和section元素
        var li =
          '<li class="liactive"><span class="tab-name">双击改名</span><span class="fa fa-times"></span></li>';
        var section =
          '<section class="conactive"><div class="fa fa-plus">' +
          (that.lis.length + 1) +
          "</div></section>";
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
    return this.each(function () {
      new Tab(this,opt||{});
    });
  };
})(jQuery);

function getTagPageConfig(id) {
  var tabs = $("#box" + id);
  var tabConfig = [];
  tabs.find(".tab-name").each(function(){
    tabConfig.push($(this).text());
  })
  return tabConfig;
}
