var files = {};
var previewIndex = 0;
var deletefiles = {};
var step = 0;
var deleteNo = 0;
var fileNum = 0;

function addPreview(input) {
    if (input[0].files.length <= (5 - ($('.preview-box').length))) {
        for (var fileIndex = 0; fileIndex < input[0].files.length; fileIndex++) {
            var file = input[0].files[fileIndex];

            if (validation(file.name))
                continue;

            setPreviewForm(file);
        }
    } else
        alert('5장만 업로드 가능합니다.');
}

function setPreviewForm(file, img) {
    var reader = new FileReader();
    reader.onload = function (img) {
        var imgNum = ++step;

        $("#preview").append(
            "<div class=\"preview-box\" id=\"newImg\" value=\"" + imgNum + "\">" +
            "<img class=\"thumbnail\" id=\"imgName\" src=\"" + img.target.result + "\"\/>" +
            "<p>" + file.name + "</p>" +
            "<a href=\"#\" value=\"" + imgNum + "\" onclick=\"deletePreview(this)\">" +
            "삭제" + "</a>"
            + "</div>"
        );
        files[fileNum] = file;
        fileNum++;
    };
    reader.readAsDataURL(file);
}


function deleteOldPreview(obj) {
    var imgNum = obj.attributes['value'].value;
    var imgName = $("#preview .preview-box[value=" + imgNum + "] .thumbnail").attr("src");
    var idx = imgName.lastIndexOf('=');
    var deleteImg = imgName.substring(idx + 1);

    console.log("deleteImg : " + deleteImg);

    deletefiles[deleteNo] = deleteImg;
    deleteNo++;

    $("#preview .preview-box[value=" + imgNum + "]").remove();
}

function deletePreview(obj) {
    var imgNum = obj.attributes['value'].value;
    delete files[imgNum];

    $("#preview .preview-box[value=" + imgNum + "]").remove();
    resizeHeight();
}

function validation(fileName) {
    fileName = fileName + "";
    var fileNameExtensionIndex = fileName.lastIndexOf('.') + 1;
    var fileNameExtension = fileName.toLowerCase().substring(
        fileNameExtensionIndex, fileName.length);
    if (!((fileNameExtension === 'jpg')
        || (fileNameExtension === 'gif') || (fileNameExtension === 'png') || (fileNameExtension === 'jpeg'))) {
        alert('jpg, gif, png 확장자만 업로드 가능합니다.');
        return true;
    } else {
        return false;
    }
}

$(document).ready(function () {
    var imageModifyNo = $("#imageModifyNo").val();

    console.log("imageModifyNo : " + imageModifyNo);

    if(imageModifyNo != undefined){
        $.getJSON("/imageBoard/attachList", {imageNo: imageModifyNo}, function (arr) {

            $(arr).each(function (i, attach) {
                $("#preview").append(
                    "<div class=\"preview-box\" value=\"" + attach.imageStep + "\">" +
                    "<img class=\"thumbnail\" id=\"imgName\" src=\"/imageBoard/display?image=" + attach.imageName + "\"\/>" +
                    "<p>" + attach.oldName + "</p>" +
                    "<a href=\"#\" value=\"" + attach.imageStep + "\" onclick=\"deleteOldPreview(this)\">" +
                    "삭제" + "</a>" +
                    "</div>"
                );
                step = attach.imageStep;
            });
        })
            .fail(function (err) {
                alert(err.responseText);
            })
    }


    $("#imageModify").click(function () {
        console.log("imageModify");
        var form = $('#uploadForm')[0];
        var formData = new FormData(form);

        for (var index = 0; index < Object.keys(files).length; index++) {

            formData.append('files', files[index]);
        }

        for (var index = 0; index < Object.keys(deletefiles).length; index++) {
            formData.append('deleteFiles', deletefiles[index]);
        }

        $.ajax({
            type: 'put',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            url: '/imageBoard/imageModify',
            data: formData,
            success: function (result) {

                if (result === -2) {
                    alert("파일이 10MB를 초과하였습니다.");
                } else if(result == 0 || result == -1){
                    alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
                } else {
                    location.href = "/imageBoard/imageDetail?imageNo=" + result;
                } 

            }
        });
    });

    $("#imageInsert").on('click', function () {
        var form = $('#uploadForm')[0];
        var formData = new FormData(form);

        for (var index = 0; index < Object.keys(files).length; index++) {
            formData.append('files', files[index]);
        }

        // console.log("file : " + formData.get('files').name);
        /*console.log("title : " + formData.get('imageTitle'));
        console.log("content : " + formData.get('imageContent'));*/


        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            processData: false,
            contentType: false,
            cache: false,
            url: '/imageBoard/imageInsert',
            data: formData,
            success: function(data){
           		 if(data == 0 || data == -1){
                    alert("오류가 발생했습니다.\n문제가 계속되면 관리자에게 문의해주세요.");
          		}else{
					  location.href="/imageBoard/imageDetail?imageNo=" + data;
				  }

            },
            error: function (request, status, error) {
                alert("code : " + request.status + "\n"
                    + "message : " + request.responseText + "\n"
                    + "error : " + error);
            }
        });
    });

    $('#attach input[type=file]').change(function () {
        addPreview($(this));
    });
});


$(function (){
    $("#insertBtn").click(function() {
        location.href = "/imageBoard/imageInsert";
    })
})

$(function () {
    $("#modify").click(function () {
        var imageNo = $("#imageNo").val();

        location.href = "/imageBoard/imageModify?imageNo=" + imageNo;
    })
});

$(function () {
    $("#delete").click(function () {
        var imageNo = $("#imageNo").val();

        $.ajax({
            url: '/imageBoard/imageDelete?imageNo=' + imageNo,
            method: 'delete',
            success: function(result){
                if(result == "success")
                    location.href= '/imageBoard/imageList';
                else if(result == "fail")
                    alert('게시글 삭제에 실패했습니다.\n 문제가 계속 발생하면 관리자에게 문의 부탁드립니다.');
                
            }
        })
    })
});


