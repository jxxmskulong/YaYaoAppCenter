/**
 * 应用中心JS
 */
//初始化书籍频道1经典必玩，2热门推荐，3，最热必玩
mainApp.value('appTypes',[
	{id:1,value:'安卓'},
	{id:2,value:'IOS'},
	{id:3,value:'H5'}
	]);
mainApp.config(function ($stateProvider, $urlRouterProvider) {
	var requestDomainUrl="http://"+domainManager.YaYaoAppCenter;//请求数据url
	var imgUploadDomainUrl="http://"+domainManager.MyWangEditor;//请求图片上传url
	
     	$stateProvider
        .state("main.appList", {//列表
            url:"/appList",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_list.html",
                    controller:function($rootScope,$scope,$state,appTypes){
            $scope.appTypes=appTypes;
            //列表
            $scope.appList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showdailyDataListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.appListInit();
			  }
			};
			$scope.appListInit=function(){
			  $.get(requestDomainUrl+"/app/count",function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/app/list?pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.appList=pld;
			 console.log($scope.appList)
			$scope.$apply();
               });
       });
			};
			$scope.appListInit();
            //列表end
            
			
            //修改
            $scope.updateApp=function(app){
            $state.go("main.appUpdate",{appId:app.appId});
            };
            //修改end
            //删除
            $scope.delApp=function(app){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/app/delete?appId="+app.appId,function(data){
       					console.log($scope.appList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };
            //删除end
            
                    }
                } 
            }
        })
        .state("main.appUpdate", {//更新
            url:"/appUpdate/:appId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_update.html",
                    controller:function($rootScope,$scope,$state,appTypes){
                    	$scope.appTypes=appTypes;
                    //获取参数appId
                    $scope.updateAppId=$state.params.appId;
                   // console.log($scope.updateAppId)
                     if(!$scope.updateAppId||!myUtils.userVerification.catNum.test($scope.updateAppId)){
                    $state.go("main.appList");
                    return;
                    } 
                    
            		//获取参数appId end
            		
                     
            		
            		//初始化app
                    $scope.updateInit=function(appId){
                    $.get(requestDomainUrl+"/app/"+appId,function(data){
                    	if(data.code==200){
                    		$scope.app=data.list[0];
                    		$scope.$apply();
                    		myUtils.myLoadingToast("加载成功" ); 
                    	}else{
                    		myUtils.myLoadingToast("加载失败");   	   				
                    	}
                    });
                    };
                    $scope.updateInit($scope.updateAppId);
                    
            		//初始化dailyData end
            		
            		
            		//修改app提交
            		$scope.updateAppForm=function(){
            		$.ajax({
            			url:requestDomainUrl+"/app/update",
            			type:'POST',
            			contentType:'application/json',
                    	data:angular.toJson($scope.app),
                    	//data:JSON.parse(angular.toJson($scope.app)),
                    	success:function(data){
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("修改成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("修改失败");   	   				
       	   				}
       	   				}});
            		};
            		
            		//修改app提交 end
            		 
            		//上传应用图片
            		//上传封面图片
             		$("#appImgFileUpload").change(function(){
             			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
             					&&($scope.imgConfigWidth>1200
             					||$scope.imgConfigWidth<=0
             					||$scope.imgConfigHeight<=0
             					||$scope.imgConfigHeight>1200)
             					){
             			myUtils.myLoadingToast("图片尺寸不符合");
            			 return;
            			 }
             			myUtils.fileUpload(
             				    {inputfile:$("#appImgFileUpload"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#appImgFileUpload").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
             				        success:function(data){
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           $scope.app.imgAddress=data;
             				          $rootScope.formDisabled=false;
             				            $scope.$apply();
             				            }
             				        }
             				    }
             				}
             				);
             		});
             		//上传详情图片
             		$("#appImgFileUploadDetails").change(function(){
             			if(($scope.imgConfigWidthDetails && $scope.imgConfigHeightDetails)
             					&&($scope.imgConfigWidthDetails>1200
             					||$scope.imgConfigWidthDetails<=0
             					||$scope.imgConfigHeightDetails<=0
             					||$scope.imgConfigHeightDetails>1200)
             					){
             			myUtils.myLoadingToast("图片尺寸不符合");
            			 return;
            			 }
             			//最多上传五张
             			 if($scope.app.appImgList.length>=5){
             			 myUtils.myLoadingToast("最多上传五张");
             			 return;
             			 }
             			myUtils.fileUpload(
             				    {inputfile:$("#appImgFileUploadDetails"),
             				    ajaxObj:{
             				        formData:[
             				            {key:"editorUpload",value:$("#appImgFileUploadDetails").get(0).files[0]}
             				            ],
             				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidthDetails+"&height="+$scope.imgConfigHeightDetails,
             				        success:function(data){
			                 			var img={
			                 			appImgId:'',
			                 			imgAddress:'',
			                 			orderNum:'',
			                 			appId:''
			                 			};
             				            if(data){
             				            myUtils.myPrevToast("上传成功",null,"remove");
             				           var maxImg=$scope.app.appImgList[$scope.app.appImgList.length-1];
               				          if(maxImg){
                				        	 img.orderNum=maxImg.orderNum+1; 
                				          }else{
                				        	  img.orderNum=1;
                				          }
             				           img.imgAddress=data;
             				            $scope.app.appImgList.push(img);
             				            console.log(JSON.parse(angular.toJson($scope.app.appImgList)))
             				            $scope.$apply();
             				            }
             				        }
             				    }
             				}
             				);
             		});
             		
             		/**
             	*删除图片
        		*/
        		$scope.delAppImg=function(appImg){
        		myUtils.myLoginOut("确定删除吗？", function(){
   				$.get(requestDomainUrl+"/appImg/delete?appImgId="+appImg.appImgId,function(data){
   					console.log($scope.appImgList)
   	   				if(data.code==200){
   	   				myUtils.myLoadingToast("删除成功", function(){
   	   				location.reload();
   	   				}); 
   	   				
   	   				}else{
   	   					myUtils.myLoadingToast("删除失败");   	   				
   	   				}
   	   			});
   			});
        		};
            		
                    }
                } 
            }
        })
        .state("main.appAdd", {//应用增加
            url:"/appAdd",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/app_add.html",
                    controller:function($rootScope,$scope,$state,appTypes){
                    	$scope.appTypes=appTypes;
				    $scope.app={
				    platform:$scope.appTypes[0].id,
				    source:'',
				    version:'',
				    title:'',
				    capacity:'',
				    divideIntoProportion:'0.5',
				    url:'',
				    imgAddress:'',
				    status:1,
				    appImgList:[],
				    content:''
				    };
                     //获取参数dailyData end
                     
                		//上传封面图片
                 		$("#appImgFileUpload").change(function(){
                 			if(($scope.imgConfigWidth && $scope.imgConfigHeight)
                 					&&($scope.imgConfigWidth>1200
                 					||$scope.imgConfigWidth<=0
                 					||$scope.imgConfigHeight<=0
                 					||$scope.imgConfigHeight>1200)
                 					){
                 			myUtils.myLoadingToast("图片尺寸不符合");
                			 return;
                			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#appImgFileUpload"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#appImgFileUpload").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidth+"&height="+$scope.imgConfigHeight,
                 				        success:function(data){
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           $scope.app.imgAddress=data;
                 				          $rootScope.formDisabled=false;
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                       
                 		
                 		//上传详情图片
                 		$("#appImgFileUploadDetails").change(function(){
                 			if(($scope.imgConfigWidthDetails && $scope.imgConfigHeightDetails)
                 					&&($scope.imgConfigWidthDetails>1200
                 					||$scope.imgConfigWidthDetails<=0
                 					||$scope.imgConfigHeightDetails<=0
                 					||$scope.imgConfigHeightDetails>1200)
                 					){
                 			myUtils.myLoadingToast("图片尺寸不符合");
                			 return;
                			 }
                 			//最多上传五张
                 			 if($scope.app.appImgList.length>=5){
                 			 myUtils.myLoadingToast("最多上传五张");
                 			 return;
                 			 }
                 			myUtils.fileUpload(
                 				    {inputfile:$("#appImgFileUploadDetails"),
                 				    ajaxObj:{
                 				        formData:[
                 				            {key:"editorUpload",value:$("#appImgFileUploadDetails").get(0).files[0]}
                 				            ],
                 				        url:imgUploadDomainUrl+"/img/add?width="+$scope.imgConfigWidthDetails+"&height="+$scope.imgConfigHeightDetails,
                 				        success:function(data){
				                 			var img={
				                 			appImgId:'',
				                 			imgAddress:'',
				                 			orderNum:'',
				                 			appId:''
				                 			};
                 				            if(data){
                 				            myUtils.myPrevToast("上传成功",null,"remove");
                 				           var maxImg=$scope.app.appImgList[$scope.app.appImgList.length-1];
                   				          if(maxImg){
                    				        	 img.orderNum=maxImg.orderNum+1; 
                    				          }else{
                    				        	  img.orderNum=1;
                    				          }
                 				           img.imgAddress=data;
                 				            $scope.app.appImgList.push(img);
                 				            console.log(JSON.parse(angular.toJson($scope.app.appImgList)))
                 				            $scope.$apply();
                 				            }
                 				        }
                 				    }
                 				}
                 				);
                 		});
                 		
                 		/**
                 	*删除图片
            		*/
            		$scope.delAppImg=function(appImg){
            		myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/appImg/delete?appImgId="+appImg.appImgId,function(data){
       					console.log($scope.appImgList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            		};
                     //表单提交
                    $scope.addAppForm=function(){
                    	$.ajax({
						  url: requestDomainUrl+"/app/add",
						  type: 'POST',
						  contentType:'application/json',
						  data: angular.toJson($scope.app),
						  success: function(data){
						  if(data.code==200){
							  $scope.app={
									    platform:$scope.appTypes[0].id,
									    source:'',
									    version:'',
									    title:'',
									    capacity:'',
									    divideIntoProportion:'',
									    url:'',
									    imgAddress:'',
									    status:1,
									    appImgList:[],
									    content:''
									    };
       	   				$scope.myAddAppForm.$setPristine();
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("添加成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("添加失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("添加失败"); 
						  }
						});
                    };
                    }
                } 
            }
        })
        	.state("main.dailyDataList", {//每日数据列表
            url:"/dailyDataList/:appId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/daily_data_list.html",
                    controller:function($rootScope,$scope,$state){
        	/*
    		*获取参数dailyDataId
    		*/
            $scope.appId=$state.params.appId;
            console.log($scope.appId)
             if(!$scope.appId||!myUtils.userVerification.catNum.test($scope.appId)){
            $state.go("main.appList");
            return;
            } 
            /*
    		*获取参数dailyDataId end
            /*
            *列表
            */
            $scope.dailyDataList=[]; 		
    		$scope.noMore=false;//false还有，true没有更多
    		$scope.totalNumber=0;//总管理员数目
 			$scope.showPageNumberArray=[];//显示页面循环的数组 类似 1,2,3,4,5
			$scope.totalPage=0;//总页数
				
			$scope.currentPage=1;//当前页
			$scope.pageNumber=10;//每页显示数目
			$scope.mostShowPageNumber=5;//设定最多显示页码数目	
			$scope.pagination=myUtils.myPaginationHandler();
			
			//点击哪页显示哪页
			$scope.toPage=function(currentPageNumber,appId){
			  if($scope.pagination.toPage(currentPageNumber,$scope.currentPage,$scope.totalPage)){
			  //$scope.showdailyDataListIcon=true;
			  $scope.currentPage=$scope.pagination.currentPage;
			  $scope.dailyDataListInit(appId);
			  }
			};
			$scope.dailyDataListInit=function(appId){
			  $.get(requestDomainUrl+"/dailyData/count?appId="+appId,function(cd){
           		$scope.totalNumber=cd;             
           //页码初始化
          $scope.totalPage=$scope.pagination.getTotalPage($scope.totalNumber,$scope.pageNumber);//总页码数目     
          $scope.showPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.mostShowPageNumber,$scope.currentPage);//显示页码数目 
          //所有页码
          $scope.showAllPageNumberArray=$scope.pagination.getShowPageNumber($scope.totalPage,$scope.pageNumber,$scope.totalPage,$scope.currentPage);
        //初始化
  $.get(requestDomainUrl+"/dailyData/list?appId="+appId+"&pageNum="+(($scope.currentPage-1)*$scope.pageNumber+1)+"&pageSize="+$scope.pageNumber,function(pld){
           $scope.dailyDataList=pld;
			 console.log($scope.dailyDataList)
			$scope.$apply();
               });
       });
			};
			$scope.dailyDataListInit($scope.appId);
			/*
            *列表end
            */
			/*
            *修改
            */
            $scope.updateDailyData=function(dailyData){
            $state.go("main.dailyDataUpdate",{dailyDataId:dailyData.dailyDataId});
            };
			/*
            *修改end
            */
			/*
            *删除
            */
           /* $scope.deldailyData=function(dailyData){
            	myUtils.myLoginOut("确定删除吗？", function(){
       				$.get(requestDomainUrl+"/dailyData/delete?dailyDataId="+dailyData.dailyDataId,function(data){
       					console.log($scope.dailyDataList)
       	   				if(data.code==200){
       	   				myUtils.myLoadingToast("删除成功", function(){
       	   				location.reload();
       	   				}); 
       	   				
       	   				}else{
       	   					myUtils.myLoadingToast("删除失败");   	   				
       	   				}
       	   			});
       			});
            };*/
			/*
            *删除end
            */
                    }
                } 
            }
        })
     	.state("main.dailyDataAdd", {//每日数据增加
            url:"/dailyDataAdd/:appId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/daily_data_add.html",
                    controller:function($rootScope,$scope,$state){
                    	/*
                		*获取参数dailyDataId
                		*/
                        $scope.appId=$state.params.appId;
                        console.log($scope.addDailyDataId)
                         if(!$scope.appId||!myUtils.userVerification.catNum.test($scope.appId)){
                        $state.go("main.appList");
                        return;
                        } 
                        /*
                		*获取参数dailyDataId end
                		*/
                      //初始化datetimerpicker
                    	//记录时间：  
                    	$('#recordDateId').datetimepicker({
                    		minView: "month",
                    		language: 'zh-CN',
                    		format: 'yyyy-mm-dd',
                    		//format: 'yyyy-mm-dd hh:mm:ss',
                    		autoclose: true
                    	});  
				    $scope.dailyData={
				    		registerNumber:'',
				    		appId:$scope.appId,
				    		recharge:''
				    };
                     
                     //表单提交
                    $scope.addDailyDataForm=function(){
                    	//console.log($scope.dailyData.recordDate)
                    	//console.log(myUtils.timeStampToDate(new Date($scope.dailyData.recordDate)))
                    	//return ;
                    	 $scope.dailyData.recordDate=myUtils.timeStampToDate(new Date($scope.dailyData.recordDate));
                    	$.ajax({
						  url: requestDomainUrl+"/dailyData/add",
						  type: 'POST',
						  data: $scope.dailyData,
						  success: function(data){
						  if(data.code==200){
							  $scope.dailyData={
									  registerNumber:'',
									  appId:$scope.appId,
							    		recharge:''
									    };
       	   				$scope.myAddDailyDataForm.$setPristine();
       	   				$scope.$apply();
       	   				myUtils.myLoadingToast("添加成功"); 
       	   				}else{
       	   					myUtils.myLoadingToast("添加失败");   	   				
       	   				}
						  },
						  error: function(){
						  myUtils.myLoadingToast("添加失败"); 
						  }
						});
                    };
                    }
                } 
            }
        })
        .state("main.dailyDataUpdate", {//更新
            url:"/dailyDataUpdate/:dailyDataId",
            views: {
            	'rightbody@main': {
                    templateUrl: "/seller/templates/appCenter/daily_data_update.html",
                    controller:function($rootScope,$scope,$state){
                  	/*
            		*获取参数dailyDataId
            		*/
                    $scope.updateDailyDataId=$state.params.dailyDataId;
                    console.log($scope.updateDailyDataId)
                     if(!$scope.updateDailyDataId||!myUtils.userVerification.catNum.test($scope.updateDailyDataId)){
                    $state.go("main.dailyDataList");
                    return;
                    } 
                    /*
            		*获取参数dailyDataId end
            		*/
            		/*
            		*初始化dailyData
            		*/
                    $scope.updateInit=function(dailyDataId){
                    $.get(requestDomainUrl+"/dailyData/"+dailyDataId,function(data){
       	   				if(data.code==200){
       	   				$scope.dailyData=data.list[0];
       	   				$scope.dailyData.recordDate=myUtils.timeStampToDayDate($scope.dailyData.recordDate);
       	   				$scope.$apply();
       	   					//初始化datetimerpicker
                    	$('#recordDateId').datetimepicker({
                    		minView: "month",
                    		language: 'zh-CN',
                    		format: 'yyyy-mm-dd',
                    		autoclose: true
                    	}); 
       	   				myUtils.myLoadingToast("加载成功" ); 
       	   				}else{
       	   					myUtils.myLoadingToast("加载失败");   	   				
       	   				}
       	   			});
                    };
                    $scope.updateInit($scope.updateDailyDataId);
                    /*
            		*初始化dailyData end
            		*/
            		/*
            		*修改dailyData提交
            		*/
            		$scope.updateDailyDataForm=function(){
            		$scope.dailyData.recordDate=myUtils.timeStampToDate(new Date($scope.dailyData.recordDate));
            		
            		$.post(requestDomainUrl+"/dailyData/update",
            				JSON.parse(angular.toJson($scope.dailyData)),
            				function(data){
            			if(data.code==200){
            				myUtils.myLoadingToast("修改成功"); 
            				location.reload();
            			}else{
            				myUtils.myLoadingToast("修改失败");   	   				
            			}
            		});
           		};
            		/*
            		*修改dailyData提交 end
            		*/
                    }
                } 
            }
        });
     	});	