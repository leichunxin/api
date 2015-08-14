
(function ($) {
	$(function () {
		nav(); //��������
		sideSlider(); //���������
	});
	
	function nav() {
		var $liCur = $(".nav-box>ul>li.cur"),
			curP = $liCur.position().left,
			curW = $liCur.outerWidth(true),
			$slider = $(".nav-line"),
			$targetEle = $(".nav-box>ul>li:not('.last')"),
			$navBox = $(".nav-box");
		$slider.stop(true, true).animate({
			"left" : curP,
			"width" : curW
		});
		$targetEle.mouseenter(function () {
			var $_parent = $(this);//.parent(),
			_width = $_parent.outerWidth(true),
			posL = $_parent.position().left;
			$slider.stop(true, true).animate({
				"left" : posL,
				"width" : _width
			}, "fast");
		});
		
	};
	
	
	function sideSlider() {
		if (!$(".help-side dl").length) {
			return false;
		}
		var $aCur = $(".help-side dl").find(".cur a"),
			$targetA = $(".help-side dl dd a"),
			$sideSilder = $(".side-slider"),
			curT = $aCur.position().top+1;
		$sideSilder.stop(true, true).animate({
			"top" : curT
		});
		$targetA.mouseenter(function () {
			var posT = $(this).position().top+1;
			$sideSilder.stop(true, true).animate({
				"top" : posT
			}, 240);
		}).parents(".help-side").mouseleave(function (_curT) {
			_curT = curT
				$sideSilder.stop(true, true).animate({
					"top" : _curT
				});
		});
	};

})(jQuery);



//�˵�����ʱ,��ʾ�Ӳ˵�
$(function() {
    var zzsc = 0; // Ĭ��ֵΪ0�������˵����»�����ʾ��ֵΪ1��������˵����ϻ�����ʾ
    if (zzsc == 0) {
        $('.nav-box ul li').hover(function() {
            $('.second', this).css('top', '76px').show();
        }, function() {
            $('.second', this).delay(0).hide(0);
        });
    } else if (zzsc == 1) {
        $('.nav-box ul li').hover(function() {
            $('.second', this).css('bottom', '76px').show();
        }, function() {
            $('.second', this).hide();
        });
    }
});

//����ͼƬ�����ķ��
myFocus.set({
    id: 'myFocus', //ID
    pattern: 'mF_tbhuabao'//���

    //mF_tbhuabao
});