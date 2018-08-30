export function loginContentsTemplate(){
return `<div class="main-login main-center">
        <form method="post" action="#">
            <div class="form-group">
                <label class="control-label">이메일</label>
                <div class="cols-sm-10">
                    <div class="input-group">
                        <input type="text" class="form-control" name="email"
                               placeholder="이메일을 입력하세요"/>
                    </div>
                    <strong name="invalid-email" style="color:red; visibility:hidden;">이메일 형식이 올바르지 않습니다.</strong>
                </div>
            </div>

            <div class="form-group">
                <label class="cols-2 control-label">비밀번호</label>
                <div class="cols-sm-10">
                    <div class="input-group">
                        <input type="password" class="form-control" name="password"
                               placeholder="비밀번호를 입력하세요(영어 소문자, 숫자, 특수문자 포함 8~16자)"/>
                    </div>
                    <strong name="invalid-password" style="color:red; visibility:hidden;">유효하지 않은 비밀번호입니다.</strong>
                </div>
            </div>

            <div class="form-group ">
                <button target="_blank" type="button" id="button"
                        class="btn btn-primary btn-lg btn-block login-button" disabled="true">로그인
                </button>
            </div>

        </form>
    </div>`;
}