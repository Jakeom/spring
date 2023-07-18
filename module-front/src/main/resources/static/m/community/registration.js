$(document).ready(function () { // 해당 카테고리에서 글 등록 시 카테고리 default
    // communityType 파라미터에 따른 카테고리 default
    let type = $("input[name=type]").val();
    $("select[name=communityType]").val(type).trigger('change');

    // positionId 파라미터에 따른 채용공고 default
    let passPositionCompanySeq = $("input[name=passPositionCompanySeq]").val();
    $("select[name=position]").val(passPositionCompanySeq).trigger('change');
});

$("select[name=communityType]").change(function () {
    let type = $(this).val();

    // 이 회사가 궁금해요 -> 비밀글 설정란 노출
    if (type == "COMPANY_CURIOUS") {
        $("#secretChk").css("display", "");     // 비밀글 설정 체크박스
        $("#searchCompany").css("display", ""); // 회사 입력란
    } else {
        $("#secretChk").css("display", "none"); // 비밀글 설정 체크박스
        $("#searchCompany").css("display", "none"); // 회사 입력란
    }

    // 합격후기 -> 채용공고 선택란 노출
    if (type == "PASS_REVIEW") {
        $("div[name=position]").css("display", "");
    } else {
        $("div[name=position]").css("display", "none");
    }
});

// (합격 후기) 채용공고 선택 시 set companySeq
$("select[name=position]").change(function () {
    console.log("selected companySeq >> " + $(this).val());
    $("input[name=companySeq]").val($(this).val());
});

// 기업명 검색시 검색결과 팝업
$("input[name=companyNames]").keyup(function () {
    let self = $(this).closest(".company-area");
    let val = $(this).val().trim();

    if (val == "") {
        return false;
    }
    $.ajax({
        url: "/fo/common/company/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function (d) {
            self.find(".search-result-list.company ul").empty();
            if (d.data.length == 0) {
                let li = '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="niceCompanyInput">(찾아보기)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);
            } else {
                $(d.data).each(function () {
                    let li = '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-name="' + this.companyName + '" data-industry="' + this.industry + '"  data-seq="' + this.id + '">';
                    li += '                <p class="company-name">' + this.companyName + '</p>';
                    li += '                <p class="company-obj">' + this.companyScale + ' | ' + this.industry + '</p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    self.find(".search-result-list.company ul").append(li);
                });

                let li = '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name"><a href="javascript:;" data-role="niceCompanyInput">더 찾아보기</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);
            }
        },
    });
});

// 나이스 기업 검색
$(document).on("click", "[data-role=niceCompanyInput]", function () {
    let val = $("input[name=companyNames]").val().trim();
    let self = $(this).closest(".company-area");
    $.ajax({
        url: "/fo/common/nice-company/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function (d) {
            console.log(d);
            self.find(".search-result-list.company ul").empty();
            if (d.data.length == 0) {
                let li = '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="directCompanyInput">(직접입력)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                self.find(".search-result-list.company ul").append(li);
            } else {
                $(d.data).each(function () {
                    const companyScale = {
                        1: '대기업'
                        , 2: '중소기업'
                        , 3: '중견기업'
                        , 0: '대상아님'
                    };

                    let li = '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-name="' + this.korentrnm + '" data-industry="' + this.sanup + '">';
                    li += '                <p class="company-name">' + this.korentrnm + '</p>';
                    li += '                <p class="company-obj">' + companyScale[this.scaledivcd] + ' | ' + this.sanup + '</p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    self.find(".search-result-list.company ul").append(li);
                });
            }
        },
    });
});

// 기업선택시
$(document).on("click", "[data-role=btnSelectCompany]", function () {
    $("input[name=companyNames]").val($(this).data("name"));
    $("input[name=companySeq]").val($(this).data("seq"));
    $(".search-result-list.company ul").empty();
});

// 직접 입력
$("[data-role=directCompanyInput]").click(function () {
    let self = $(this).closest(".company-area");
    self.find(".search-result-list.company ul").empty();
});

// 포커스 아웃시 창 닫기
$(document).on("focusout", "input[name=companyNames]", function () {
    setTimeout(function () {
        $(".search-result-list.company ul").empty();
    }, 500);
});

// 이미지
$(document).on('change', '[data-role=upFile]', function (e) {
//$("[data-role=upFile]").change(function(e){
    let seq = $(this).data('seq')
    var ext = $(this).val().split('.').pop().toLowerCase();
    if ($.inArray(ext, ['gif', 'png', 'jpg', 'jpeg']) == -1) {
        alert('등록할 수 없는 파일입니다.');
        $("#communityFormFile" + seq).val("");
        return false;
    }
    let reader = new FileReader();
    let files = e.target.files;
    let filesArr = Array.prototype.slice.call(files);
    if ($("input[name=files]").length < 4) { // 파일 최대갯수 3장제한
        filesArr.forEach(function (f) {
            reader.onload = function (e) {
                var appedHtml = '<div class="preview-image" id="preview-image' + seq + '" style="background-image: url(' + e.target.result + ')">';
                appedHtml += '<div class="hover-close">';
                appedHtml += '<button type="button" data-delseq="' + seq + '" data-role="delImg"><i class="icon-del"></i></button>';
                appedHtml += '</div></div>';
                $(".preview-image-wrap").append(appedHtml)
                $(".form-upload").find('input[id=communityFormFile' + seq + ']').hide();
                $(".form-upload").find('label[for=communityFormFile' + seq + ']').hide();
                $(".form-upload").append('<input type="file" data-role="upFile" data-seq="' + (seq + 1) + '" name="files" id="communityFormFile' + (seq + 1) + '"/>');
                $(".form-upload").append('<label for="communityFormFile' + (seq + 1) + '"><i class="icon-input-add"></i></label>');
            }
            reader.readAsDataURL(f);
        });
    } else {
        alert("파일은 최대 3장까지 등록가능합니다.");
        $("#communityFormFile"+seq).val("");
        return false;
    }
});

$(document).on('click', '[data-role=delImg]', function (e) {
    let delSeq = $(this).data('delseq');
    $('#preview-image' + delSeq).remove();
    $('#communityFormFile' + delSeq).remove();
})

// 등록하기 버튼 클릭 시
$(document).on('click', 'button[data-role=registration]', function (e) {
    let os = Utils.checkMobile();
    console.log(os);
    let type = $("select[name=communityType] option:selected").val();
    let title = $("input[name=title]").val().trim();
    let content = $("textarea[name=content]").val().trim();
    let position = $("select[name=position] option:selected").val();
    let companySeq = $("input[name=companySeq]").val();

    if ($("input[name=secretChk]").is(":checked") && type == "COMPANY_CURIOUS") { // 비밀글 설정 체크 시 secretFlag Y
        $("input[name=secretFlag]").val("Y");
    }

    if (type != "COMPANY_CURIOUS" && type != "PASS_REVIEW") { // 합격후기&이 회사가 궁금해요 제외 companySeq null
        $("input[name=companySeq]").val("");
    }

    if (type == "") {
        alert("카테고리를 선택해주세요.");
        return false;
    }

    if (type == "PASS_REVIEW" && position == "") {
        alert("채용공고를 선택하세요.");
        return false;
    }

    if (type == "COMPANY_CURIOUS" && companySeq == "") {
        alert("회사 이름을 입력하세요.");
        $("input[name=companyNames]").focus();
        return false;
    }

    if (title == "") {
        alert("제목을 입력하세요.");
        $("input[name=title]").focus();
        return false;
    }

    if (content == "") {
        alert("내용을 입력하세요");
        $("textarea[name=content]").focus();
        return false;
    }


    // 이 회사가 궁금해요 -> 해당 회사를 포지션 등록한 헤드헌터의 존재유무 확인
    if (type == "COMPANY_CURIOUS") {
        let param = {
            companySeq: $("input[name=companySeq]").val()
        }
        $.ajax({
            type: "post",
            url: "/m/position/existByCompany",
            contentType: "application/json",
            data: JSON.stringify(param),
            success: function (data) {
                if (data.serviceCode == "NOT_EXISTS") { // 존재X confirm문구 변경
                    if (!confirm("해당 기업을 포지션 등록한 헤드헌터가 없으므로 댓글이 없을 수 있습니다. 등록하시겠습니까?")) {
                        return false;
                    } else {
                        register();
                    }
                } else { // 존재O
                    if (!confirm("게시글을 등록하시겠습니까?")) {
                        return false;
                    } else {
                        register();
                    }
                }
            },
            error: function () {
                alert("*****ajax fail*****");
            }
        });
    } else { // 나머지 카테고리
        if (!confirm("게시글을 등록하시겠습니까?")) {
            return false;
        } else {
            register();
        }
    }
})

function register() {
    let form = $('form[name=communityForm]')[0];
    let formData = new FormData(form);

    $.ajax({
        url: "/m/community/insert",
        type: "post",
        data: formData,
        contentType: false,
        processData: false,
        success: function (data) {
            if (data.status == 200) {
                let os = Utils.checkMobile();
                if (os == "ANDROID") {
                    window.Resume9.complete();
                } else if (os == "IOS") {
                    window.webkit.messageHandlers.Resume9.postMessage({
                        'action': 'complete'
                    });
                }
            } else {
                alert("오류가 발생하였습니다. 관리자에게 문의하세요.")
            }
        },
        error: function () {
            alert("오류가 발생하였습니다. 관리자에게 문의하세요.");
        }
    });
}