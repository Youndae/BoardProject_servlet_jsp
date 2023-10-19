$(function(){
    $('.pagination a').on('click', function(e) {
        e.preventDefault();

        var page_form = $('#page_action');

        page_form.find("input[name='pageNum']").val($(this).attr("href"));

        page_form.submit();
    });

    $("#insertBtn").click(function(e){
        e.preventDefault();
        $("#insertForm").submit();
    })

    $("#loginBtn").click(function(){
        location.href='/member/login';
    })
})

