/**
 * Created by ACER on 2017/11/20.
 */

(function($) {
    var downloadUrl=['服务器请求地址1','服务器请求地址2'];

    $(downloadUrl).multiDownload();

    var methods = {

        _download: function(options) {

            var triggerDelay = (options && options.delay) || 100;

            var removeDelay = (options && options.removeDelay) || 1000;

            this.each(function(index, item) {

                methods._createIFrame(item, index * triggerDelay, removeDelay);

            });

        },

        _createIFrame: function(url, triggerDelay, removeDelay) {

            //动态添加iframe，设置src，然后删除

            setTimeout(function() {

                var frame = $('<iframe style="display: none;" class="multi-download"></iframe>');

                frame.attr('src', url);

                $(document.body).after(frame);

                setTimeout(function() {

                    frame.remove();

                }, removeDelay);

            }, triggerDelay);

        }

    };

    $.fn.multiDownload = function(options) {

        methods._download.apply(this, arguments);

    };
})(jQuery);