function post(){
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();
    // console.log(questionId);
    // console.log(commentContent);
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": questionId,
            "content": commentContent,
            "type": 1
        }) ,
        success: function (response) {
            if (response.code === 4599){
                $("#commentSection").hide();
            }else if(response.code === 2003){
                var conf = confirm(response.message);
                if(conf){
                    window.open("https://github.com/login/oauth/authorize?client_id=61fefd6a91c82539e61f&redirect_uri=http://localhost:8081/callback&scope=user&state=1");
                    window.localStorage.setItem("closeOrNot", "true");
                }
            }else{
                alert(response.code);
            }
            console.log(response);
        },
        dataType: "json",
        contentType: "application/json"
    });
}