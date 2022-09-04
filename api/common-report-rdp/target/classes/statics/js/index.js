//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props:{item:{}},
    template:[
        '<li>',
        '	<a v-if="item.type === 0" href="javascript:;">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '		<i class="fa fa-angle-left pull-right"></i>',
        '	</a>',
        '	<ul v-if="item.type === 0" class="treeview-menu">',
        '		<menu-item :item="item" v-for="item in item.list"></menu-item>',
        '	</ul>',

        '	<a v-if="item.type === 1 && item.parentId === 0" :href="\'#\'+item.url">',
        '		<i v-if="item.icon != null" :class="item.icon"></i>',
        '		<span>{{item.name}}</span>',
        '	</a>',

        '	<a v-if="item.type === 1 && item.parentId != 0 && (item.openMode==\'_self\'||item.openMode==\'\'||item.openMode==null )" :href="\'#\'+item.url" :target="item.openMode" ><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '	<a v-if="item.type === 1 && item.parentId != 0 && item.openMode!=\'_self\'&&item.openMode!=\'\'&&item.openMode!=null" :href="item.url" :target="item.openMode" ><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join('')
});

//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height() - 155);
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

//注册菜单组件
Vue.component('menuItem',menuItem);

var vm = new Vue({
	el:'#rrapp',
	data:{
		user:{},
		menuList:{},
		main:"main.html",
		password:'',
		newPassword:'',
        navTitle:"控制台"
	},
	methods: {
		getMenuList: function (event) {
			$.getJSON("sys/menu/nav?_"+$.now(), function(r){
				$.each(r.menuList,function(i,obj){
					if(obj.url!=null&&obj.url!==undefined){
						r.menuList[i].url = urlParam(obj.url,vm.user);
					}
					if(obj.list!=null&&obj.url!==undefined){
						$.each(obj.list,function(j,o){
							if(o.url!=null&&o.url!==undefined){
								r.menuList[i].list[j].url = urlParam(o.url,vm.user);
							}
						})
					}
				});
				vm.menuList = r.menuList;
			});
		},
		getUser: function(){
			$.getJSON("sys/user/info?_"+$.now(), function(r){
				vm.user = r.user;
			});
		},
		getVersion: function(){
			$.getJSON("sys/config/version?_"+$.now(), function(r){
				$("#logo-lg-ver").html(r.version);
				$("#logo-mini-ver").html("Ver:"+r.version);
			});
		},
		updatePassword: function(){
			layer.open({
				type: 1,
				//skin: 'layui-layer-lan',
				title: "修改密码",
				area: ['550px', '270px'],
				shadeClose: false,
				content: jQuery("#passwordLayer"),
				btn: ['修改','取消'],
				btn1: function (index) {
					var data = "password="+vm.password+"&newPassword="+vm.newPassword;
					$.ajax({
						type: "POST",
					    url: "sys/user/password",
					    data: data,
					    dataType: "json",
					    success: function(result){
							if(result.code == 0){
								layer.close(index);
								layer.alert('修改成功', function(index){
									location.reload();
								});
							}else{
								layer.alert(result.msg);
							}
						}
					});
	            }
			});
		},
        donate: function () {
            layer.open({
                type: 2,
                title: false,
                area: ['806px', '467px'],
                closeBtn: 1,
                shadeClose: false,
                content: ['', 'no']
            });
        }
	},
	created: function(){
		this.getMenuList();
		this.getUser();
		this.getVersion();
	},
	updated: function(){
		//路由
		var router = new Router();
		routerList(router, vm.menuList);
		router.start();
	}
});

function urlParam(url,obj){
	var newurl = url;
	var reg = /[^\{\}]+(?=\})/g;
	var params = url.match(reg);
	newurl = newurl.replace(/&amp;/g,"&");
	$.each(params,function(i,para){
		var val = obj[para];
		var colmun = "${"+para+"}";
		if(newurl.indexOf(colmun)!=-1){
			newurl = newurl.replace(colmun,val);
		}
	});
	return newurl;
}

function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
			routerList(router, menu.list);
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    vm.main = url.replace('#', '');
			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
