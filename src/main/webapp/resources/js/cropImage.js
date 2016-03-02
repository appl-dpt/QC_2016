var $imageAreaSelect;
var bufferedCanvas;
var bufferedContext

function cropImage(image, x, y, width, height) {
    var canvas = document.getElementById('imageCanvas');
    var context = canvas.getContext('2d');

    canvas.width = width;
    canvas.height = height;

    console.log(canvas.width);
    console.log(canvas.height);
    console.log(x);
    console.log(y);
    var croppedImage = bufferedContext.getImageData(x, y, width, height);

    context.putImageData(croppedImage, 0, 0);

    var imgURL = canvas.toDataURL('image/*');

    return imgURL;
}

function submitImage() {
    $("#inputImage").attr("src", $("#selectedImage").val());
    $("#inputImageSave").val($("#selectedImage").val());
    $("#image").val($("#selectedImage").val());
    $("#imageModal").modal("hide");
    $("#btnok").show();
}

function setupModal() {
    $imageAreaSelect = $("#avatarImage").imgAreaSelect({
        handles: true,
        aspectRatio: '1:1',
        instance: true,
        onSelectEnd: function(image, properties) {
            $("#selectedImage").val(cropImage(image.getAttribute("src"), properties.x1, properties.y1, properties.width, properties.height));
        }
    });
    $('#imageModal').on('shown.bs.modal', function () {
        $("#avatarImage").attr("src", $('#selectedImage').val());
    });
    $('#imageModal').on('hide.bs.modal', function () {
        $imageAreaSelect.cancelSelection();
    });
}

function showAvatarModal() {
    $("#imageFile").click();
}

$(document).ready(function() {
    setupModal();
    bufferedCanvas = document.getElementById("bufferedCanvas");
    bufferedContext = bufferedCanvas.getContext("2d");

    $("#showImagePopupBtn").click(showAvatarModal);
    $("#imageSelectBtn").click(submitImage);
    $("#imageFile").change(function() {
        if (this.files && this.files[0]) {
            var FR = new FileReader();

            FR.onload = function(e) {
                var img = new Image();

                 img.onload = function() {
                    bufferedContext.drawImage(this, 0, 0, bufferedCanvas.width, bufferedCanvas.height);
                 }

                $('#selectedImage').val(e.target.result);
                $("#imageModal").modal("show");

                img.src=e.target.result;
            };

            FR.readAsDataURL( this.files[0] );
        }
    });
});

function cancelImage() {
    $("#imageFile").val("");  
};

$(document).ready(function() {
    $("#btnok").hide();
});
    
/*$.validator.addMethod(
        "imageFile",
        function (value, element) {
            return this.optional(element) || (element.files && element.files[0]
                                   && element.files[0].size < 1024 * 1024 * 2);
        },
        alert('The file size can not exceed 2MB.')
    );*/
