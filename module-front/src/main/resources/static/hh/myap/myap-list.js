let hhFieldsLow = "";
$(document).ready(function(){
    hhFieldsLow = $('#modal-headhunter').find('span[name=hhFieldsLow]').detach();
})


$("input").on("keyup",function(key){
    if(key.keyCode==13) {
        $("button[data-role=search]").click();
    }
});

// 페이지 이동
$('a[data-role=btnGoPage]').click(function () {
    var page = $(this).data("page");
    $("form[name=searchForm] input[name=page]").val(page);
    $("form[name=searchForm]").submit();
});

// 페이지 크기 변경시
$("select[name=rowSize]").change(function(){
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 등록경로 변경시
$("select[name=registerPathCd]").change(function(){
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 검색
$("button[data-role=search]").click(function() {
    $("form[name=searchForm] input[name=page]").val(1);
    $("form[name=searchForm]").submit();
});

// 초기화
$("button[data-role=reset]").click(function() {
    window.location.replace(window.location.pathname);
});

// 경력 option 설정
for(var i = 0; i < 100; i++){                
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
$('select[name=ageSt]').val($('#ageSt').val());
$('select[name=ageEn]').val($('#ageEn').val());

$('.select-design').select2({
    minimumResultsForSearch: -1,
});

function groupEdit(obj){
      if($(obj).val()!=''){
          $('#modal-history-group #exampleModalLabel').text($("select[name=groupIdPop] option:selected").text());
          $("form[name=historyGroupForm] input[name=groupId]").val($(obj).val());
          $('#modal-history-group #name').text('');
          $('#modal-history-group').modal('show');
      }

      $('#groupIdPop').val('');
      $('#groupIdPop').select2({
        minimumResultsForSearch: -1,
      });
  
}

function addMyap(){
    if(getCheckBoxCnt() == 0){
        alert('먼저 선택을 해주세요.')
        return;
    }
}

// 이동처리
function groupAdd(mode){
    if((mode == 'add' || mode == 'modi') && $('form[name=historyGroupForm] #name').val().length == 0){
        alert('그룹명의 입력해주세요');
        return;
    }
    
    var groupId = $("form[name=historyGroupForm] input[name=groupId]").val();

    if(mode == 'add'){
        $("form[name=historyGroupForm] input[name=id]").val(null);
    }else if(mode=='modi'){
        $("form[name=historyGroupForm] input[name=id]").val(groupId);
        $("form[name=historyGroupForm] input[name=delFlag]").val('N');
    }else if(mode=='delete'){
        $("form[name=historyGroupForm] input[name=id]").val(groupId);
        $("form[name=historyGroupForm] input[name=delFlag]").val('Y');
    }else{
        return;
    }

    // 그룹 등록/수정/삭제
    $.ajax({
        url: '/hh/myap/addHistoryGroup',
        type: 'POST',
        data: $("form[name=historyGroupForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                window.location.reload();
            } else if(data.status == 405){
                alert('기본 그룹은 삭제 할수 없습니다.');
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}


// 그룹 이동처리
function groupMove(){
    $('#multipleCheckAll').is('checked');

    if(getCheckBoxCnt() == 0){
        alert('대상을 선택 해주세요.')
        return;
    }

    // 체크된 값 채우기
    $("form[name=historyGroupForm] input[name=checkedFields]").val(getCheckBoxValues());

    // 이동처리
    $.ajax({
        url: '/hh/myap/moveHistoryGroup',
        type: 'POST',
        data: $("form[name=historyGroupForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
}


// 체크박스 전체 처리
function allCheck(){
    var _is = $('#multipleCheckAll').is(':checked');
    $( '._talbeCheckbox' ).each(function() {
        $( this ).prop("checked",_is);
    });    
}



// 체크된 박스 숫자 체크 
function getCheckBoxCnt(){
    var _isTrueCnt = 0;
    $( '._talbeCheckbox' ).each(function() {
        if($( this ).is(':checked')){
            _isTrueCnt++;
        }
    });
    return _isTrueCnt;
}

// 체크된 박스 값 리턴 
function getCheckBoxValues(){
    var checkedFields = '';
    $( '._talbeCheckbox' ).each(function() {
        if($( this ).is(':checked')){
            if(checkedFields != ''){
                checkedFields = checkedFields + ',';
            }
            checkedFields = checkedFields + $( this ).val();
        }
    });
    return checkedFields;
}

// 체크박스 처리
$('._talbeCheckbox').on('change', function(){
    if($( '._talbeCheckbox' ).length  == getCheckBoxCnt()){
        $('#multipleCheckAll').prop("checked",true);
    }else{
        $('#multipleCheckAll').prop("checked",false);
    }
});

// 인재등록
function addMyAp(){
    //window.location.href= '/hh/apsearch/ap-registration';
    window.location.href= '/hh/apsearch/ap-registration-bot';

    
}

// 이력서 비공개하기   opened 0 비공개  1공개
function openMyap(obj, opened){
    $("form[name=openMyapForm] input[name=resumeId]").val($(obj).data("resumeid"));
    $("form[name=openMyapForm] input[name=opened]").val(opened);
    $.ajax({
        url: '/hh/myap/resumeOpened',
        type: 'POST',
        data: $("form[name=openMyapForm]").serialize(),
        success: function(data) {
            if(data.status == 200){
                alert("변경이 완료되었습니다.");
                window.location.reload();
            } else {
                alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
            }
        },
        error: function(){
            alert('오류가 발생하였습니다. 관리자에게 문의하세요.');
        }
    });
};
