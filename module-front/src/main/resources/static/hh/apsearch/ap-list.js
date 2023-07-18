// 전역변수 선언부
let companyIntroduce = $("div.company-introduce-area").clone();
let contact = $('div.contact-area').clone();

$("select").selectAjax({});

// 기업명 검색시 검색결과 팝업
$("input[name=companyName]").keyup(function(){
    let self = $(".content");
    let val = $(this).val().trim();

    if($("input[name=directInput]").val() == "1"){
        return false;
    }

    if(val == ""){
        return false;
    }

    $.ajax({
        url: "/fo/common/company/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            $(".search-result-list.company ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="niceCompanyInput">(찾아보기)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                $(".search-result-list.company ul").append(li);
            } else {
                $(d.data).each(function(){
                    let li =  '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-name="' + this.companyName + '" data-industry="' + this.industry + '">';
                    li += '                <p class="company-name">' + this.companyName + '</p>';
                    li += '                <p class="company-obj">' + this.companyScale + ' | ' + this.industry + '</p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    $(".search-result-list.company ul").append(li);
                    $(".search-result-list.company ul li:last a[data-role=btnSelectCompany]").data(this);
                });

                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name"><a href="javascript:;" data-role="niceCompanyInput">더 찾아보기</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                $(".search-result-list.company ul").append(li);

                let li2 =  '<li>';
                li2 += '        <div class="result-company">';
                li2 += '            <a href="javascript:;">';
                li2 += '                <p class="company-name"><a href="javascript:;" data-role="directCompanyInput">직접입력</a></p>';
                li2 += '            </a>';
                li2 += '        </div>';
                li2 += '    </li>';
                $(".search-result-list.company ul").append(li2);
            }
        },
    });
});

// 나이스 기업 검색
$(document).on("click", "[data-role=niceCompanyInput]", function(){
    let val =  $("input[name=companyNames]").val().trim();
    let self = $(".content");
    $.ajax({
        url: "/fo/common/nice-company/list",
        type: "GET",
        data: {
            searchValue: val
        },
        success: function(d){
            $(".search-result-list.company ul").empty();
            if(d.data.length == 0){
                let li =  '<li>';
                li += '        <div class="result-company">';
                li += '            <a href="javascript:;">';
                li += '                <p class="company-name">검색결과가 없습니다. <a href="javascript:;" data-role="directCompanyInput">(직접입력)</a></p>';
                li += '            </a>';
                li += '        </div>';
                li += '    </li>';
                $(".search-result-list.company ul").append(li);
            } else {
                $(d.data).each(function(){
                    const companyScale = {
                        1: '대기업'
                        , 2: '중소기업'
                        , 3: '중견기업'
                        , 0: '대상아님'
                    };

                    const companyStatus = {
                        '00':'정상'
                        , '01':'업종변경'
                        , '02':'사업자번호 폐업후 재사용'
                        , '03':'피흡수합병'
                        , 11:'법인전환'
                        , 12:'조직변경'
                        , 13:'상속'
                    };

                    const marketListing = {
                        1: '코스피'
                        , 2: '코스닥'
                        , 3: '코넥스'
                        , 4: '제3시장'
                        , 9: '대상아님'
                    };

                    let li =  '<li>';
                    li += '        <div class="result-company">';
                    li += '            <a href="javascript:;" data-role="btnSelectCompany" data-name="' + this.korentrnm + '" data-industry="' + this.sanup + '">';
                    li += '                <p class="company-name">' + this.korentrnm + '</p>';
                    li += '                <p class="company-obj">' + companyScale[this.scaledivcd] + ' | ' + this.sanup + '</p>';
                    li += '            </a>';
                    li += '        </div>';
                    li += '    </li>';
                    $(".search-result-list.company ul").append(li);

                    $(".search-result-list.company ul li:last a[data-role=btnSelectCompany]").data({
                        name: this.korentrnm,
                        licenseNumber: this.business,
                        ceo : this.korreprnm,
                        establishDate : this.obz_date,
                        phone : this.tel,
                        address : this.koraddr,
                        companyScale : companyScale[this.scaledivcd],
                        industry : this.sanup,
                        marketListing : marketListing[this.ltgmktdivcd],
                        companyStatus : companyStatus[this.eprdatastsdivcd],
                    });
                });
            }
        },
    });
});

// 직접 입력
$(document).on("click", "[data-role=directCompanyInput]", function(){
    $(".search-result-list.company ul").empty();
    $("input[name=directInput]").val("1");
    $(".license-area").show();
});

// 포커스 아웃
$("input[name=companyName]").focusout(function(){
    setTimeout(function(){
        $(".search-result-list.company ul").empty();
    }, 500);
});

// 기업선택시
$(document).on("click", "[data-role=btnSelectCompany]", function(){
    $("input[name=companyName]").val($(this).data("name"));
    $("input[name=licenseNumber]").val($(this).data().licenseNumber);
    $("input[name=ceo]").val($(this).data().ceo);
    $("input[name=establishDate]").val($(this).data().establishDate);
    $("input[name=phone]").val($(this).data().phone);
    $("input[name=address]").val($(this).data().address);
    $("input[name=companyScale]").val($(this).data().companyScale);
    $("input[name=companyIndustry]").val($(this).data().industry);
    $("input[name=marketListing]").val($(this).data().marketListing);
    $("input[name=companyStatus]").val($(this).data().companyStatus);
    $(".search-result-list.company ul").empty();
});


$("input").on("keyup",function(key){
    if(key.keyCode==13) {
        $("button[data-role=search]").click();
    }
});

$('a[data-role=btnGoPage]').click(function () {
    var page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
    $(".nav-tabs li a.active").trigger("click");
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 초기화
$("a[data-role=reset]").click(function() {
    $.ajax({
        url: '/hh/apsearch/reset',
        type: 'POST',
        success: function(data) {
            if(data.status == 200){
                window.location.reload();
            }else{
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
});

// 경력 option 설정
for(var i = 0; i <= 30; i++){
    var option = $("<option value='"+i+"'>"+i+"년</option>");
    $('select[name=careerSt]').append(option);
    $('select[name=careerEn]').append(option.clone());
}

$('select[name=careerSt]').val($('#careerSt').val()==''?'':$('#careerSt').val());
$('select[name=careerEn]').val($('#careerEn').val()==''?'':$('#careerEn').val());
// 연령
var year = new Date().getFullYear();	// 연도
var age = 20;
for(var i = year-21; i >= year-61; i--){
    var option = $("<option value='"+age+"'>"+age+"세("+i+")</option>");
    $('select[name=ageSt]').append(option);
    $('select[name=ageEn]').append(option.clone());    
    age++;
}
$('select[name=ageEn]').val($('#ageEn').val()==''?'':$('#ageEn').val());
$('select[name=ageSt]').val($('#ageSt').val()==''?'':$('#ageSt').val());

for(var i = 1; i <= 10; i++){
    var option = $("<option value='"+(i*1000)+"'>"+(i*1000).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')+"만원</option>");
    $('select[name=salarySt]').append(option);
    $('select[name=salaryEn]').append(option.clone());    
}
$('select[name=salarySt]').val($('#salarySt').val()==''?'':$('#salarySt').val());
$('select[name=salaryEn]').val($('#salaryEn').val()==''?'':$('#salaryEn').val());

// 검색조건 저장
function saveFrom(){
    if($("form[name=searchForm] input[name=page]").val() == ''){
        $("form[name=searchForm] input[name=page]").val(1);
    }
    $.ajax({
        url: '/hh/apsearch/saveForm',
        type: 'POST',
        data: $("form[name=searchForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
            }else{
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });

}

var _edu2 = $('#edu2').val().split(',')??'';;
for(var i = 0; i < _edu2.length; i++){
    var _v = _edu2[i];
    if(_v.length > 0){
        $('.edu2 input[value='+_v+']').prop('checked','checked');
    }
}

var _workplace = $('#workplace').val().split(',')??'';
for(var i = 0; i < _workplace.length; i++){
    var _v = _workplace[i];
    if(_v.length > 0){
        $('.workplace input[value='+_v+']').prop('checked','checked');
    }
}

$("select[name=rowSize]").change(function(){
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});
