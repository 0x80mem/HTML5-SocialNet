const init = {

    language: "zh_CN",
    placeholder: "在这里输入文字",
    min_width: 320,
    min_height: 220,
    height: 500,
    branding: false,
    statusbar: false,
    menubar:false,
    elementpath: false,
    toolbar_location: "bottom",
    plugins: "code advlist lists image imagetools emoticons ",
    toolbar: [
      "image emoticons forecolor backcolor bold italic underline strikethrough link anchor"
    ],
    paste_data_images: true,
    file_picker_callback: function (callback) {
        // 创建一个文件选择输入框
        var input = document.createElement("input");
        input.setAttribute("type", "file");
        input.setAttribute("accept", "image/*");

        // 添加事件监听，当用户选择文件后触发回调函数
        input.addEventListener("change", function () {
            var file = input.files[0];//获得的是图片对象，可以用于传给图床
            const editorContainer = document.getElementById("content");
            editorContainer.appendChild(input);
            var reader = new FileReader();

            reader.onload = function (e) {
                // 将文件数据传递给TinyMCE编辑器
                callback(e.target.result);//传回base64文件，让图片在主界面上显示

            };

            // 读取选择的文件
            reader.readAsDataURL(file);


        });

        // 触发文件选择对话框
        input.click();
    },

}
export default init
