/**
 * submit the comment
 */
function post(){
    var questionId = $("#questionId").val();
    var commentContent = $("#commentContent").val();
    // console.log(questionId);
    // console.log(commentContent);
    comment_target(questionId, 1, commentContent);

}
function comment_target(targetId, type, content) {
    if (!content){
        alert("Content cannot be empty.");
        return;
    }
    $.ajax({
        type: "POST",
        url: "/comment",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }) ,
        success: function (response) {
            if (response.code === 4599){
                $("#commentSection").hide();
                window.location.reload();
            }else if(response.code === 2003){
                var conf = confirm(response.message);
                if(conf){
                    window.open("https://github.com/login/oauth/authorize?client_id=61fefd6a91c82539e61f&redirect_uri=http://localhost:8081/callback&scope=user&state=1");
                    window.localStorage.setItem("closeOrNot", "true");
                }
            }else{
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json",
        contentType: "application/json"
    });
}

function second_level_comment(e) {
    var commentId = e.getAttribute("data-secondLevel");
    var content = $("#second_level"+commentId).val();
    comment_target(commentId, 2, content);
}



/**
 * show or collapse the second level comments
 */
function showOrCollapse(e) {
    var id = e.getAttribute("data-id");
    var comment = $("#comment"+id);
    var state = e.getAttribute("data_show");
    if (state){
        comment.removeClass("in");
        e.removeAttribute("data_show");
        e.classList.remove("active");
    }else{
        var subCommentContainer = $("#comment" + id);
        if (subCommentContainer.children().length != 1) {
            comment.addClass("in");
            e.setAttribute("data_show", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.accoundId
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });

                comment.addClass("in");

                e.setAttribute("data_show", "in");
                e.classList.add("active");
            });
        }
    }
}
function showTags() {
    $("#allTags").show();
}


function selectTag(e) {
    var value = e.getAttribute("data-tag");
    var existed = $("#tag").val();
    if (existed.indexOf(value) == -1){
        if (existed){
            $("#tag").val(existed+','+value);
        }else{
            $("#tag").val(value);
        }
    }

}