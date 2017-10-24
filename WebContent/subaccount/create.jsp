
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>创建子账户</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<link rel="shortcut icon" href="/images/favicon.ico" type="image/x-icon"/>
	<link href="/css/store.css" rel="stylesheet" type="text/css" />
	<link href="/css/layout.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" href="/css/jquery.validation.css" />

	<link href="/css/ie_style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/js/jquery-2.1.0.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.js"></script>
	<link href="/css/bootstrapValidator.min.css" rel="stylesheet" />
	<link rel="stylesheet" href="/css/bootstrap.css" media="screen" />
</head>

<body>

	<div id="main">
		<!-- header -->
		<script type="text/javascript">
			$(function() {
				$('#header').load('/load/header.html');
				$('#header2').load('/load/header2.html');
				$('#foot').load('/load/foot.html');
			});
		</script>
		<div id="header"></div>
		<div id="content">
			<div id="header2"></div>
			<div class="wrapper">
				<div class="aside">
					<ul class="nav001">
						<li><a href="#none">创建子账户</a></li>
						<li><a href="#none">查询子账户</a></li>
					</ul>
				</div>
				<div class="mainContent">
					<br /> <br />
					<form action="/auth/subaccount/create.action"
						class="validation-form-container" enctype="multipart/form-data"
						method="post" style="width: 55%; text-align: center">

						<br>
						<div class="field">
							<div class="ui left labeled input">
								<input type="text" id ="username" name="phone" placeholder="手机号/用户名" />

								<div class="ui corner label">
									<i class="asterisk icon">*</i>
								</div>
							</div>
						</div> <br>
						
						<div class="field">
							<div class="ui left labeled input">
								<input type="password" id="password" name="password" placeholder="密码" />

								<div class="ui corner label">
									<i class="asterisk icon">*</i>
								</div>
							</div>
						</div> <br>
						
												
						<div class="field">
							<div class="ui left labeled input">
								<input type="password" id="confirmPwd" name="confirmPwd" placeholder="确认密码" />

								<div class="ui corner label">
									<i class="asterisk icon">*</i>
								</div>
							</div>
						</div> <br>
						
						<button type="submit" class="btn btn-primary" style="width: 45%; float: left">
							<strong>&nbsp;创建子账户&nbsp;</strong>
						</button> 
						
						<c:if test="${not empty errorMsg}">
							<div class="login-error-tip">${errorMsg}</div>
						</c:if>
						
					</form>

				</div>
			</div>
		</div>
		<!-- footer -->
		<div id="foot"></div>
	</div>
	<script type="text/javascript" src="http://kefu.puckart.com/mibew/js/compiled/chat_popup.js"></script>
	<script type="text/javascript" src="/js/kf.js"></script>
	<script src="/js/bootstrapValidator.min.js"></script>
	    <script type="text/javascript">
        $(document).ready(function() {
            $('#defaultForm')
                .bootstrapValidator({
                    message: '用户名或密码错误',

                    feedbackIcons: {
                        valid: 'fa fa-check',
                        invalid: 'fa fa-close',
                        validating: 'fa fa-circle-o-notch fa-spin'
                    },
                    fields: {
                        username: {
                            message: 'The username is not valid',
                            validators: {
                                notEmpty: {
                                    message: '用户名不能为空'
                                },
                                stringLength: {
                                    min: 6,
                                    max: 30,
                                    message: '用户名为6~30位'
                                },
                                threshold: 6, //input>6,post ajax
                                remote: { //server result:{"valid",true or false}
                                    url: 'http://magic.puckart.com/auth/checkphone.action',
                                    message: '用户名已存在',
                                    delay: 500,
                                    type: 'POST'
                                        /* data: function(validator) {
                                              return {
                                                  password: $('[name="passwordNameAttributeInYourForm"]').val(),
                                              };
                                           } */
                                },
                                regexp: {
                                    regexp: /^([a-zA-Z0-9_\.-]+)$/,
                                    message: '请不要使用非法字符'
                                }
                            }
                        },
                        password: {
                            message: '密码无效',
                            validators: {
                                notEmpty: {
                                    message: '密码不能为空'
                                },
                                stringLength: {
                                    min: 6,
                                    message: '至少输入6位密码'
                                },
                                different: {
                                    field: 'username',
                                    message: '请不要使用与用户名相同的密码'
                                }
                            }
                        },
                        confirmPwd: {
                            notEmpty: '密码无效',
                            validators: {
                                notEmpty: {
                                    message: '用户名不能为空'
                                },
                                stringLength: {
                                    min: 6,
                                    message: '至少输入6位密码'
                                },
                                identical: {
                                    field: 'password',
                                    message: '两次输入的密码不一致'
                                },
                                different: {
                                    field: 'username',
                                    message: '不能和用户名相同'
                                }
                            }
                        },
                    }
                })
                // .on('success.form.bv', function(e) {
                //
                //     e.preventDefault();
                //
                //     var $form = $(e.target),
                //         validator = $form.data('bootstrapValidator');
                //     $form.find('.alert').html('注册中...').show();
                //
                //     $.post($form.attr('action'), $form.serialize(), function(result) {
                //         console.log(result);
                //         if (true == result) {
                //             $form.find('.alert').html('很高兴见到你，' +validator.getFieldElements('username').val() +
                //             '，欢迎加入Freework，' + '即将跳转...').show();
                //         }
                //     }, 'json');
                //
                // });
        });
    </script>
	
	
</body>
</html>
