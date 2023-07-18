$("button[data-role=modify]").on("click", function () {
    location.href = "/bo/management/operation/resume/status/resume-edit/" + $("input[name=resumeId]").val()
})