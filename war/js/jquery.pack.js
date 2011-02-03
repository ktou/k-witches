/**
 * jQuery lightpop plugin
 * This jQuery plugin was inspired on jQuery lightBox plugin by Leandro Vieira Pinho (http://leandrovieira.com/projects/jquery/lightpop/)
 * @name jquery.lightpop-0.4.0.js
 * @author wokamoto - http://dogmap.jp
 * @version 0.4.0
 * @date January 17, 2008
 * @category jQuery plugin
 * @copyright (c) 2007-2008 wokamoto (dogmap.jp)
 * @license  Released under the GPL license (http://www.gnu.org/copyleft/gpl.html)
 * @example http://dogmap.jp/lightpop_sample/sample.html
 */
(function(jQuery){
jQuery.fn.lightpop = function(settings){
 settings = jQuery.extend({
   overlayBgColor:  '#000'
  ,overlayOpacity:  0.7
  ,contentFrameType:    'border'
  ,contentBorder:   'none'
  ,boxBorderSize:   6
  ,containerBorderSize: 10
  ,containerResizeSpeed:'normal'
  ,contentBgColor:  '#FFF'
  ,imageBox:        'images/lightpop-box.gif'
  ,imageBorderL:    'images/lightpop-border-l.gif'
  ,imageBorderR:    'images/lightpop-border-r.gif'
  ,imageLoading:    'images/lightpop-ico-loading.gif'
  ,imageBtnPrev:    'images/lightpop-btn-prev.gif'
  ,imageBtnNext:    'images/lightpop-btn-next.gif'
  ,imageBtnClose:   'images/lightpop-btn-close.gif'
  ,imageBlank:      'images/lightpop-blank.gif'
  ,imageBtnPrevWidth:   63
  ,imageBtnNextWidth:   63
  ,imageBtnCloseWidth:  66
  ,txtImage:        null
  ,txtOf:       'of'
  ,setLinkToTitle:  false
  ,keyToClose:      'c'
  ,keyToPrev:       'p'
  ,keyToNext:       'n'
  ,flvplayer:       'flvplayer.swf'
  ,iconAdd:     true
//  ,iconImage:     'images/icon-image.png'
//  ,iconVideo:     'images/icon-video.png'
//  ,iconContents:  'images/icon-contents.png'
//  ,iconYouTube:   'images/icon-youtube.png'
//  ,iconMetacafe:  'images/icon-metacafe.png'
//  ,iconLiveLeak:  'images/icon-liveleak.png'
//  ,iconGoogleVideo:   'images/icon-googlevideo.png'
//  ,iconifilm:     'images/icon-ifilm.png'
//  ,iconDailymotion:   'images/icon-dailymotion.png'
 }, settings);
 var frameBorder = ((/^border$/i).test(settings.contentFrameType));
 var arrContent = new Array();

 var fileTypes = new Array(
  {
   type: 'Image'
  ,match: function(strUrl){return ((/\.(jpe?g|gif|png|bmp)[\?]?.*$/i).test(strUrl));}
  ,set: function(contentNo){
    image_load(arrContent[contentNo].href, (function(n){return function(){
     var arrSizes = get_sizes_from_str(this.src, new Array(this.width, this.height));
     arrContent[n] = jQuery.extend(arrContent[n], {content:'<img src="' + this.src.replace(/\?.*$/, '') + '" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" />', width:arrSizes[0], height:arrSizes[1], later:(jQuery.browser.msie ? 250 : 100)});
     set_content_to_view(n);
     this.onload = function(){};
    }})(contentNo));
   }
  ,preload: function(contentNo){
    image_load(arrContent[contentNo].href, (function(n){return function(){
     var arrSizes = get_sizes_from_str(this.src, new Array(this.width, this.height));
     arrContent[n] = jQuery.extend(arrContent[n], {content:'<img src="' + this.src.replace(/\?.*$/, '') + '" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" />', width:arrSizes[0], height:arrSizes[1], later:(jQuery.browser.msie ? 250 : 100)});
     this.onload = function(){};
    }})(contentNo));
   }
  }
 ,{
   type: 'Video'
  ,match: function(strUrl){return ((/\.(flv|swf|rm|mov|3gp|mp4|asf|avi|mpg|wmv)[\?]?.*$/i).test(strUrl));}
  }
 ,{
   type: 'YouTube'
  ,match: function(strUrl){return ((/\.youtube\.com\/watch/i).test(strUrl));}
  }
 ,{
   type: 'metacafe'
  ,match: function(strUrl){return ((/\.metacafe\.com\/watch/i).test(strUrl));}
  }
 ,{
   type: 'LiveLeak'
  ,match: function(strUrl){return ((/\.liveleak\.com\/view/i).test(strUrl));}
  }
 ,{
   type: 'GoogleVideo'
  ,match: function(strUrl){return ((/video\.google\.com\/videoplay/i).test(strUrl));}
  }
 ,{
   type: 'IFILM'
  ,match: function(strUrl){return ((/\.ifilm\.com\/video/i).test(strUrl));}
  }
 ,{
   type: 'Dailymotion'
  ,match: function(strUrl){return ((/\.dailymotion\.com\/video/i).test(strUrl));}
  }
 ,{
   type: 'nicovideo'
  ,match: function(strUrl){return ((/\.nicovideo\.jp\/watch/i).test(strUrl));}
  }
 ,{
   type: 'Contents'
  ,match: function(strUrl){return (RegExp(window.location.host, 'i').test(strUrl));}
  ,set: function(contentNo){
    jQuery.get(arrContent[contentNo].href, function(responseText, status){
     var arrSizes = get_sizes_from_str(arrContent[contentNo].href);
     var strSrc = '<div width="' + arrSizes[0] + '" height="' + arrSizes[1] + '">' + responseText.replace(/[\r\n]/g, '').replace(/.*<body.*?>(.*?)<\/body>.*$/, '$1').replace(/<script.*?>.*<\/script>/g, '') + '</div>';
     arrContent[contentNo] = jQuery.extend(arrContent[contentNo], {content:strSrc, width:arrSizes[0], height:arrSizes[1], later:500});
     set_content_to_view(contentNo);
    });
   }
  ,preload: function(contentNo){}
  ,content_css: {textAlign:'left', lineHeight:'1.2em'}
  }
 );

 // initialize
 var initialize = function(jQueryObj){
  // image preload
  image_load(settings.imageBtnPrev,  function(){settings.imageBtnPrevWidth  = (this.width > 0 ? this.width : 63); this.onload = function(){};});
  image_load(settings.imageBtnNext,  function(){settings.imageBtnNextWidth  = (this.width > 0 ? this.width : 63); this.onload = function(){};});
  image_load(settings.imageBtnClose, function(){settings.imageBtnCloseWidth = (this.width > 0 ? this.width : 66); this.onload = function(){};});
  if (!frameBorder) {
   jQuery.each(new Array(settings.imageBox, settings.imageBorderL, settings.imageBorderR), function(){image_load(this);});
   settings.contentBorder = 'none';
  }

  // get matched object
  var domMatchedObj = new Array();
  var intImage = 0;
  var location = window.location;
  arrContent.length = 0;
  jQueryObj.filter('a').each(function() {
   var jQuery_this = jQuery(this);
   if (!(/^https?:/i).test(jQuery_this.attr('href')) && (/^https?:/i).test(location.protocol)) {
    jQuery_this.attr('href', (
     (/^\//i).test(jQuery_this.attr('href'))
       ? location.protocol + '//' + location.hostname + '/' + jQuery_this.attr('href')
       : location.href.replace(/^(.*\/).*$/i, '$1') + jQuery_this.attr('href').replace(/^\/?(.*)$/i, '$1')
    ));
   }

   for (var filetype = fileTypes[0], optindex = 0; optindex < fileTypes.length; optindex++, filetype = fileTypes[optindex]) {
    if (filetype.match(jQuery_this.attr('href'))) {
     // set icons to link
     if (settings.iconAdd && jQuery_this.children('img').length == 0) {
      jQuery_this.css({background:'transparent url(' + (settings['icon' + filetype.type] ? settings['icon' + filetype.type] :'images/icon-' + filetype.type.toLowerCase() + '.png') + ') no-repeat scroll 1px 0pt', paddingLeft:'20px'});
     }

     // content info
     arrContent.push({
      index: optindex,
      type:  filetype.type,
      href:  jQuery_this.attr('href'),
      title: (jQuery_this.attr('title') ? jQuery_this.attr('title') : jQuery_this.html().replace(/<[^>]*>/ig, ''))
     });

     // bind click event
     jQuery_this.unbind('click').click((function(i){return function(){start_lightpop(i); return false;}})(intImage));

     // push array
     domMatchedObj.push(this);
     intImage++;
     break;
    }
   }
  });
  return new jQuery(domMatchedObj);
 };

 // start!
 var start_lightpop = function(intClickedContent){
  // hide embed, object, select element
  set_visibility(jQuery('embed, object, select'), false);

  // set interface
  jQuery('body').append(
   jQuery('<div id="overlay" /><div id="lightpop"><div id="lightpop-box"><div id="lightpop-box-content">' + (
    frameBorder
      ? '<div id="lightpop-content-box"><div id="lightpop-content" /></div></div><div id="lightpop-data-box"><div id="lightpop-data"><div id="lightpop-details"><div id="lightpop-details-caption" /><div id="lightpop-details-number" /></div><div id="lightpop-data-nav"><a href="#" id="lightpop-data-nav-close"><img src="'+settings.imageBtnClose+'" /></a></div></div></div>'
      : '<div id="lightpop-box-hd"><div id="lightpop-box-hdc" /></div><div id="lightpop-box-bd"><div id="lightpop-box-bdc"><div id="lightpop-content-box"><div id="lightpop-content" /></div><div id="lightpop-data-box"><div id="lightpop-data"><div id="lightpop-details"><div id="lightpop-details-caption" /><div id="lightpop-details-number" /></div><div id="lightpop-data-nav"><a href="#" id="lightpop-data-nav-close"><img src="'+settings.imageBtnClose+'" /></a></div></div></div></div></div><div id="lightpop-box-ft"><div id="lightpop-box-ftc" /></div></div>'
   ) + '<div id="lightpop-nav"><a href="#" id="lightpop-nav-prev" /><a href="#" id="lightpop-nav-next" /></div><div id="lightpop-loading"><a href="#" id="lightpop-loading-link"><img src="'+settings.imageLoading+'" /></a></div></div></div>').hide()
  );

  // set interface CSS
  var arrPageSizes  = get_page_sizes();
  var arrPageScroll = get_page_scroll();
  var initSize = 250;

  // overlay
  jQuery('#overlay').css({position:'absolute', top:0, left:0, backgroundColor:settings.overlayBgColor, opacity:settings.overlayOpacity, width:arrPageSizes[0], height:arrPageSizes[1], zIndex:90});
  jQuery('#lightpop').css({position:'absolute', top:arrPageScroll[1] + Math.round(arrPageSizes[3] / 10), left:arrPageScroll[0], width:'100%', textAlign:'center', lineHeight:0, border:'none', zIndex:100});
  jQuery('#lightpop a img').css({border:'none'});

  // container
  jQuery('#lightpop-box').css({position:'relative', width:initSize, height:initSize, top:0, margin:'0 auto', padding:0, backgroundColor:settings.contentBgColor, border:settings.contentBorder, overflow:(frameBorder ? 'hidden' : 'visible')});
  jQuery('#lightpop-content-box').css({backgroundColor:'transparent'});
  jQuery('#lightpop-content').css({margin:(frameBorder ? 0 : '0 auto'), padding:(frameBorder ? 0 : settings.containerBorderSize + 'px 0')});
  jQuery('#lightpop-box-content').css({position:'relative', width:initSize, height:initSize, backgroundColor:'transparent', margin:'0 auto', padding:(frameBorder ? settings.containerBorderSize : 0), overflow:(frameBorder ? 'hidden' : 'visible')});
  if (!frameBorder) {
   set_box_css(false).css({position:'relative'}).hide();
   jQuery('#lightpop-box-hd').css({height:settings.boxBorderSize, top:0, margin:'0 ' + settings.boxBorderSize + 'px 0 0'});
   jQuery('#lightpop-box-hdc').css({height:settings.boxBorderSize, top:0, margin:'0 ' + settings.boxBorderSize*-1 + 'px 0 ' + settings.boxBorderSize + 'px'});
   jQuery('#lightpop-box-ft').css({height:settings.boxBorderSize, bottom:0, margin:'0 ' + settings.boxBorderSize + 'px 0 0'});
   jQuery('#lightpop-box-ftc').css({height:settings.boxBorderSize, bottom:0, margin:'0 ' + settings.boxBorderSize*-1 + 'px 0 ' + settings.boxBorderSize + 'px'});
  }

  // navigation
  jQuery('#lightpop-nav').css({position:'absolute', top:0, left:0, height:'10px', width:'100%', padding:0, margin:(frameBorder ? '0' : settings.boxBorderSize + 'px') + ' auto', zIndex:10});
  jQuery('#lightpop-nav a').css({display:'block', height:'100%', zoom:1, margin:(frameBorder ? 0 : '0 ' + settings.boxBorderSize + 'px'), outline:'none'});
  jQuery('#lightpop-nav-prev').css({width:settings.imageBtnPrevWidth, left:0, styleFloat:'left'});
  jQuery('#lightpop-nav-next').css({width:settings.imageBtnNextWidth, right:0, styleFloat:'right'});

  // loading image
  jQuery('#lightpop-loading').css({position:'absolute', top:'40%', left:0, height:'20%', width:'100%', margin:'0 auto', textAlign:'center', lineHeight:0});

  // content data
  jQuery('#lightpop-data-box').css({font:'10px Verdana, Helvetica, sans-serif', backgroundColor:settings.contentBgColor, lineHeight:'1.4em', width:'100%', margin:'0 auto', padding:'0 ' + settings.containerBorderSize + 'px 0', overflow:'hidden'}).hide();
  jQuery('#lightpop-data').css({position:'relative', padding:'0 ' + settings.containerBorderSize + 'px', color:'#666', left:0, bottom:0});
  jQuery('#lightpop-details').css({width:'70%', styleFloat:'left', textAlign:'left'});
  jQuery('#lightpop-details-caption').css({styleFloat:'left', 'font-weight':'bold', width:'100%'});
  jQuery('#lightpop-details-number').css({styleFloat:'left', clear:'left', width:'100%', 'padding-bottom':'1.0em'});
  jQuery('#lightpop-data-nav-close').css({styleFloat:'right', width:settings.imageBtnCloseWidth, 'padding':'0.35em 0'});

  // bind event
  jQuery('#overlay').click(finish_lightpop).fadeIn(settings.containerResizeSpeed, function(){jQuery('#lightpop').show();});
  jQuery('#lightpop-loading-link, #lightpop-data-nav-close').click(function(){finish_lightpop(); return false;});
  window_resize(true);

  // set content
  set_content(intClickedContent);
 };

 // set content
 var set_content = function(contentNo){
  set_visibility(jQuery('#lightpop-data, #lightpop-details, #lightpop-details-caption, #lightpop-details-number, #lightpop-data-nav-close'), false);
  jQuery('#lightpop-nav, #lightpop-nav-prev, #lightpop-nav-next').hide();
  jQuery('#lightpop-content').hide().children().remove();
  jQuery('#lightpop-loading, #lightpop-box, #lightpop-box-content').show();
  set_box_css(false);
  if(!frameBorder && jQuery.browser.safari){jQuery('#lightpop-data-box').height('auto');}
  set_content_to_view(contentNo);
 };

 // set content to view
 var set_content_to_view = function(contentNo){
  (arrContent[contentNo].content
    ? function(n){jQuery('#lightpop-content').append(jQuery(arrContent[n].content)); setTimeout(function(){show_container(n);}, arrContent[n].later);}
    : (fileTypes[arrContent[contentNo].index].set ? fileTypes[arrContent[contentNo].index].set : (function(contentNo){set_video_info(contentNo); set_content_to_view(contentNo);}))
  )(contentNo);
 };

 // show container
 var show_container = function(contentNo){
  var intWidth  = arrContent[contentNo].width  + (settings.containerBorderSize + (frameBorder ? 0 : settings.boxBorderSize)) * 2;
  var intHeight = arrContent[contentNo].height + settings.containerBorderSize * 2;

  jQuery('#lightpop-box').animate({width:intWidth, height:intHeight}, settings.containerResizeSpeed, function(){
   var contentInfo = arrContent[contentNo];

   // resize content and data
   jQuery('#lightpop-box-content').css({width:(frameBorder ? contentInfo.width : intWidth), height:contentInfo.height});
   jQuery('#lightpop-content').css({width:contentInfo.width, height:contentInfo.height});
   jQuery('#lightpop-data-box').css({width:contentInfo.width});

   // set content css
   if (fileTypes[contentInfo.index].content_css) {jQuery('#lightpop-content').children().css(fileTypes[contentInfo.index].content_css);}

   // show content
   jQuery('#lightpop-loading').hide();
   if ((/<object.*>/i).test(contentInfo.content)) {jQuery('#lightpop-content').show();} else {jQuery('#lightpop-content').fadeIn();}
   set_box_css(true);

   // set content data
   jQuery('#lightpop-details-caption').html((settings.setLinkToTitle ? '<a href="' + contentInfo.href + '" title="' + contentInfo.title + '">' + contentInfo.title + '</a>' : contentInfo.title));
   jQuery('#lightpop-details-number').html((settings.txtImage ? settings.txtImage : contentInfo.type) + (arrContent.length > 1 ? ' ' + (contentNo + 1) + ' ' + settings.txtOf + ' ' + arrContent.length : ''));
   var data_box = set_visibility(jQuery('#lightpop-data, #lightpop-details, #lightpop-details-caption, #lightpop-details-number, #lightpop-data-nav-close'), false).show();
   jQuery('#lightpop-data-box').slideDown('fast', function(){
    var intDataboxHeight = (!jQuery.browser.safari ? jQuery(this).height() : this.scrollHeight);
    intDataboxHeight = (intDataboxHeight < 28 ? 28 : intDataboxHeight);
    intHeight += intDataboxHeight;
    if (frameBorder) {
     jQuery('#lightpop-box').animate({width:intWidth, height:intHeight}, 'fast', function(){
      set_visibility(data_box, true);
     });
    } else {
     if (jQuery.browser.safari) {jQuery('#lightpop-data-box').height(intDataboxHeight);}
     set_visibility(data_box, true);
    }
   });

   // set navigation
   jQuery('#lightpop-nav').css({width:intWidth}).show(function(){
    jQuery('#lightpop-nav-prev, #lightpop-nav-next').css({height:Math.round(intHeight / 3), background:'transparent url(' + settings.imageBlank + ') no-repeat'}).unbind();
    if(contentNo != 0){
     jQuery('#lightpop-nav-prev').hover(
      function(){jQuery(this).css({background:'url(' + settings.imageBtnPrev + ') left 30% no-repeat'});},
      function(){jQuery(this).css({background:'transparent url(' + settings.imageBlank + ') no-repeat'});}
     ).show().click((function(i){return function(){set_content(i); return false;}})(contentNo - 1));
    }
    if(contentNo != (arrContent.length - 1)){
     jQuery('#lightpop-nav-next').hover(
      function(){jQuery(this).css({background:'url(' + settings.imageBtnNext + ') right 30% no-repeat'});},
      function(){jQuery(this).css({background:'transparent url(' + settings.imageBlank + ') no-repeat'});}
     ).show().click((function(i){return function(){set_content(i); return false;}})(contentNo + 1));
    }
    keyboard_navigation(true, contentNo);
   });

   // preload contents
   if((arrContent.length - 1) > contentNo){preload(contentNo + 1);}
   if(contentNo > 0){preload(contentNo - 1);}
  });
 };

 // preload
 var preload = function(contentNo){
  if(!arrContent[contentNo].content) {
   (fileTypes[arrContent[contentNo].index].preload
     ? fileTypes[arrContent[contentNo].index].preload
     : (function(n){set_video_info(n);})
   )(contentNo);
  }
 };

 // get content information
 var get_sizes_from_str = function(strText, defaultSizes){
  var intWidth = 0, intHeight = 0;
  var arrText = strText.toLowerCase().replace(/[\r\n]/g,'').replace(/["']/g,'').match(/(width|height)=(\d+)/ig);
  if (arrText) {
   jQuery.each(arrText, function(){
    if ((/^width=\d+$/i).test(this))  {intWidth  = Number(this.replace(/^width=(\d+)$/, '$1'));}
    if ((/^height=\d+$/i).test(this)) {intHeight = Number(this.replace(/^height=(\d+)$/,'$1'));}
   });
  }
  if (intWidth == 0 || intHeight == 0) {
   if (defaultSizes) {
    intWidth  = defaultSizes[0];
    intHeight = defaultSizes[1];
   } else {
    var arrPageSizes = get_page_sizes();
    intWidth  = Math.round(arrPageSizes[0] / 4)
    intHeight = Math.round(arrPageSizes[1] / 4);
   }
  }
  return new Array(intWidth, intHeight);
 };

 var set_video_info = function(contentNo) {
  var contentInfo = arrContent[contentNo];
  var strUrl = contentInfo.href.replace(/&.*$/i,'');
  var arrSizes, vid, strSrc = null, intLater = 1500;
  switch(contentInfo.type){
   case 'YouTube':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(425, 355));
    vid    = strUrl.replace(/^.*\.youtube.*watch.*?v=(.*)$/i, '$1');
    strUrl = 'http://www.youtube.com/v/' + vid;
    strSrc = get_flash_src(strUrl+"&autoplay=1", arrSizes[0], arrSizes[1]);
    break;
   case 'metacafe':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(400, 345));
    vid    = strUrl.replace(/\?.*$/, '').replace(/\/$/i,'').replace(/^.*\.metacafe.*watch\/(.*)$/i, '$1');
    strUrl = 'http://www.metacafe.com/fplayer/' + vid + '.swf';
    strSrc = get_flash_src(strUrl, arrSizes[0], arrSizes[1]);
    break;
   case 'LiveLeak':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(450, 370));
    vid    = strUrl.replace(/^.*\.liveleak.*view.*?i=(.*)$/i, '$1');
    strUrl = 'http://www.liveleak.com/player.swf?autostart=false&token=' + vid;
    strSrc = get_flash_src(strUrl, arrSizes[0], arrSizes[1], {quality:"high"});
    break;
   case 'GoogleVideo':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(400, 326));
    vid    = strUrl.replace(/^.*video\.google\.com\/videoplay.*docid=(.*)$/i, '$1');
    strUrl = 'http://video.google.com/googleplayer.swf?docId=' + vid + '&#038;hl=en';
    strSrc = get_flash_src(strUrl, arrSizes[0], arrSizes[1], {flashvars:''});
    break;
   case 'IFILM':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(448, 365));
    vid    = strUrl.replace(/\?.*$/, '').replace(/\/$/i,'').replace(/^.*\.ifilm\.com.*video\/(.*)$/i, '$1');
    strUrl = 'http://www.ifilm.com/efp';
    strSrc = get_flash_src(strUrl, arrSizes[0], arrSizes[1], {flashvars:'flvbaseclip=' + vid + '&', quality:"high", bgcolor:'000000'});
    break;
   case 'Dailymotion':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(420, 330));
    vid    = strUrl.replace(/^.*\.dailymotion\.com\/video\/([^_]*).*$/i, '$1');
    strUrl = 'http://www.dailymotion.com/swf/' + vid;
    strSrc = get_flash_src(strUrl, arrSizes[0], arrSizes[1], {allowFullScreen:'true', allowScriptAccess:'always'});
    break;
   case 'nicovideo':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(312, 176));
    vid    = strUrl.replace(/^.*\.nicovideo\.jp\/watch\/(.*)$/i, '$1');
    strSrc = '<iframe width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" src="http://www.nicovideo.jp/thumb/' + vid + '" scrolling="no" style="border:solid 1px #CCC;" frameborder="0"><a href="http://www.nicovideo.jp/watch/' + vid + '">' + contentInfo.tytle + '</iframe>';
    intLater = 500;
    break;
   case 'Video':
    arrSizes = get_sizes_from_str(contentInfo.href, new Array(320, 240));
    strUrl = strUrl.replace(/\?.*$/, '');
    switch(strUrl.match(/\.(flv|swf|rm|mov|3gp|mp4|asf|avi|mpg|wmv)$/i)[1]){
     case 'flv':
      strUrl = settings.flvplayer + '?file=' + strUrl;
     case 'swf':
      strSrc = get_flash_src(strUrl, arrSizes[0], arrSizes[1], {quality:"high", bgcolor:"#000"});
      break;
     case 'rm':
      strSrc = '<object classid="clsid:cfcdaa03-8be4-11cf-b84b-0020afbbccfa" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '"><param name="src" value="' + strUrl + '" /><param name="autostart" value="true" /><param name="controls" value="imagewindow,controlpanel" /><embed src="' + strUrl + '" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" autostart="true" controls="imagewindow,controlpanel" type="audio/x-pn-realaudio-plugin"></embed></object>';
      break;
     case 'mov':
     case 'mp4':
     case '3gp':
      arrSizes[1] += 20;
      strSrc = '<object classid="clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B" codebase="http://www.apple.com/qtactivex/qtplugin.cab" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '"><param name="src" value="' + strUrl + '" /><param name="href" value="' + strUrl + '" /><param name="controller" value="true" /><param name="pluginspage" value="http://www.apple.com/quicktime/download/" /><param name="autoplay" value="true" /><param name="bgcolor" value="000000" /><embed src="' + strUrl + '" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" bgcolor="000000" autoplay="true" controller="true" src="' + strUrl + '" type="video/quicktime" pluginspage="http://www.apple.com/quicktime/download/"></embed></object>';
      break;
     default:
      arrSizes[1] += 20;
      strSrc = '<object classid="clsid:6BF52A52-394A-11d3-B153-00C04F79FAA6" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" type="application/x-oleobject"><param name="url" value="' + strUrl + '" /><param name="autostart" value="true" /><param name="uiMode" value="full" /><embed src="' + strUrl + '" width="' + arrSizes[0] + '" height="' + arrSizes[1] + '" autostart="true" uiMode="full" type="application/x-mplayer2" pluginspage="http://www.microsoft.com/windows/mediaplayer/"></embed></object>';
      break;
    }
    intLater = 1000;
    break;
  }
  if (strSrc) {arrContent[contentNo] = jQuery.extend(contentInfo, {content:strSrc, width:arrSizes[0], height:arrSizes[1], later:intLater});}
 }

 var get_flash_src = function(url, width, height, param) {
  param = jQuery.extend({movie: url, wmode: 'transparent'}, param);
  var strSrc = '<object data="' + url + '" type="application/x-shockwave-flash" width="' + width + '" height="' + height + '" wmode="' + param.wmode + '">';
  jQuery.each(param, function(key){strSrc += '<param name="' + key + '" value="' + this + '" />';})
  strSrc += '</object>';
  return strSrc;
 };

 // set box css
 var set_box_css = function(enable) {
  if (!frameBorder) {
   var jQueryObj = jQuery('#lightpop-box-hd, #lightpop-box-hdc, #lightpop-box-bd, #lightpop-box-bdc, #lightpop-box-ft, #lightpop-box-ftc');
   var bg_transparent = 'transparent', bg_content = settings.contentBgColor;
   if (enable) {
    jQuery('#lightpop-box').css({backgroundColor:bg_transparent});
    jQuery('#lightpop-box-hd').css({background:bg_transparent + ' url(' + settings.imageBox + ') left top no-repeat'});
    jQuery('#lightpop-box-hdc').css({background:bg_transparent + ' url(' + settings.imageBox + ') right top no-repeat'});
    jQuery('#lightpop-box-bd').css({background:bg_content + ' url(' + settings.imageBorderL + ') left top repeat-y'});
    jQuery('#lightpop-box-bdc').css({background:bg_transparent + ' url(' + settings.imageBorderR + ') right top repeat-y'});
    jQuery('#lightpop-box-ft').css({background:bg_transparent + ' url(' + settings.imageBox + ') left bottom no-repeat'});
    jQuery('#lightpop-box-ftc').css({background:bg_transparent + ' url(' + settings.imageBox + ') right bottom no-repeat'});
    jQueryObj.show();
   } else {
    jQuery('#lightpop-box').css({backgroundColor:bg_content});
    jQueryObj.css({background:bg_transparent});
   }
   return jQueryObj;
  }
 };

 // image loader
 var image_load = function(src, callback){
  var objImageLoader = new Image();
  if (callback) {objImageLoader.onload = callback;}
  objImageLoader.src = src;
  return objImageLoader;
 }

 // set visibility
 var set_visibility = function(jQueryObj, enable){return jQueryObj.css({visibility:(enable ? 'visible' : 'hidden')});}

 // set keydown event
 var keyboard_navigation = function(enable, contentNo){
  jQuery(document).unbind('keydown');
  if (enable) {
   jQuery(document).keydown(function(objEvent){
    var key,keycode,escapeKey;
    if(!objEvent){
     keycode = event.keyCode;
     escapeKey = 27;
    } else {
     keycode = objEvent.keyCode;
     escapeKey = objEvent.DOM_VK_ESCAPE;
    }
    key = String.fromCharCode(keycode).toLowerCase();
    if ((key == settings.keyToClose) || (key == 'x') || (keycode == escapeKey)) {
     finish_lightpop();
    } else if (((key == settings.keyToPrev) || (keycode == 37)) && contentNo != 0) {
     set_content(contentNo - 1);
    } else if (((key == settings.keyToNext) || (keycode == 39)) && contentNo != (arrContent.length - 1)) {
     set_content(contentNo + 1);
    }
   });
  }
 };

 // set window resize event
 var window_resize = function(enable){
  jQuery(window).unbind('resize');
  if (enable) {
   jQuery(window).resize(function(){
    var arrPageSizes  = get_page_sizes();
    var arrPageScroll = get_page_scroll();
    jQuery('#overlay').css({width:arrPageSizes[0], height:arrPageSizes[1]});
    jQuery('#lightpop').css({top:arrPageScroll[1] + Math.round(arrPageSizes[3] / 10), left:arrPageScroll[0]});
   });
  }
 };

 // get page sizes
 var get_page_sizes = function(){
  var xScroll,yScroll, windowWidth, windowHeight, pageHeight, pageWidth;
  if (window.innerHeight && window.scrollMaxY) {
   xScroll = window.innerWidth  + window.scrollMaxX;
   yScroll = window.innerHeight + window.scrollMaxY;
  } else if (document.body.scrollHeight > document.body.offsetHeight) {
   xScroll = document.body.scrollWidth;
   yScroll = document.body.scrollHeight;
  } else {
   xScroll = document.body.offsetWidth;
   yScroll = document.body.offsetHeight;
  }
  if (self.innerHeight) {
   if(document.documentElement.clientWidth){
    windowWidth = document.documentElement.clientWidth;
   } else {
    windowWidth = self.innerWidth;
   }
   windowHeight = self.innerHeight;
  } else if (document.documentElement && document.documentElement.clientHeight) {
   windowWidth  = document.documentElement.clientWidth;
   windowHeight = document.documentElement.clientHeight;
  } else if (document.body) {
   windowWidth  = document.body.clientWidth;
   windowHeight = document.body.clientHeight;
  }
  pageWidth  = (xScroll < windowWidth  ? xScroll : windowWidth);
  pageHeight = (yScroll < windowHeight ? windowHeight : yScroll);
  return new Array(pageWidth, pageHeight, windowWidth, windowHeight);
 };

 // get page scroll
 var get_page_scroll = function(){
  var xScroll, yScroll;
  if(self.pageYOffset){
   yScroll = self.pageYOffset;
   xScroll = self.pageXOffset;
  }else if(document.documentElement && document.documentElement.scrollTop){
   yScroll = document.documentElement.scrollTop;
   xScroll = document.documentElement.scrollLeft;
  }else if(document.body){
   yScroll = document.body.scrollTop;
   xScroll = document.body.scrollLeft;
  }
  return new Array(xScroll, yScroll);
 };

 // finish!
 var finish_lightpop = function(){
  set_visibility(jQuery('object',jQuery('#lightpop')), false).remove();
  jQuery('#lightpop').slideUp(function(){
   jQuery(this).remove();
   jQuery('#overlay').fadeOut(function(){
    jQuery(this).remove();
    // show embed, object, select element
    set_visibility(jQuery('embed, object, select'), true);
   });
  });
  keyboard_navigation(false);
  window_resize(false);
 };

 return initialize(this);
};})(jQuery);

(function($){
    
$.dimensions = {
    version: '@VERSION'
};

// Create innerHeight, innerWidth, outerHeight and outerWidth methods
$.each( [ 'Height', 'Width' ], function(i, name){
    
    // innerHeight and innerWidth
    $.fn[ 'inner' + name ] = function() {
        if (!this[0]) return;
        
        var torl = name == 'Height' ? 'Top'    : 'Left',  // top or left
            borr = name == 'Height' ? 'Bottom' : 'Right'; // bottom or right
        
        return this.is(':visible') ? this[0]['client' + name] : num( this, name.toLowerCase() ) + num(this, 'padding' + torl) + num(this, 'padding' + borr);
    };
    
    // outerHeight and outerWidth
    $.fn[ 'outer' + name ] = function(options) {
        if (!this[0]) return;
        
        var torl = name == 'Height' ? 'Top'    : 'Left',  // top or left
            borr = name == 'Height' ? 'Bottom' : 'Right'; // bottom or right
        
        options = $.extend({ margin: false }, options || {});
        
        var val = this.is(':visible') ? 
                this[0]['offset' + name] : 
                num( this, name.toLowerCase() )
                    + num(this, 'border' + torl + 'Width') + num(this, 'border' + borr + 'Width')
                    + num(this, 'padding' + torl) + num(this, 'padding' + borr);
        
        return val + (options.margin ? (num(this, 'margin' + torl) + num(this, 'margin' + borr)) : 0);
    };
});

// Create scrollLeft and scrollTop methods
$.each( ['Left', 'Top'], function(i, name) {
    $.fn[ 'scroll' + name ] = function(val) {
        if (!this[0]) return;
        
        return val != undefined ?
        
            // Set the scroll offset
            this.each(function() {
                this == window || this == document ?
                    window.scrollTo( 
                        name == 'Left' ? val : $(window)[ 'scrollLeft' ](),
                        name == 'Top'  ? val : $(window)[ 'scrollTop'  ]()
                    ) :
                    this[ 'scroll' + name ] = val;
            }) :
            
            // Return the scroll offset
            this[0] == window || this[0] == document ?
                self[ (name == 'Left' ? 'pageXOffset' : 'pageYOffset') ] ||
                    $.boxModel && document.documentElement[ 'scroll' + name ] ||
                    document.body[ 'scroll' + name ] :
                this[0][ 'scroll' + name ];
    };
});

$.fn.extend({
    position: function() {
        var left = 0, top = 0, elem = this[0], offset, parentOffset, offsetParent, results;
        
        if (elem) {
            // Get *real* offsetParent
            offsetParent = this.offsetParent();
            
            // Get correct offsets
            offset       = this.offset();
            parentOffset = offsetParent.offset();
            
            // Subtract element margins
            offset.top  -= num(elem, 'marginTop');
            offset.left -= num(elem, 'marginLeft');
            
            // Add offsetParent borders
            parentOffset.top  += num(offsetParent, 'borderTopWidth');
            parentOffset.left += num(offsetParent, 'borderLeftWidth');
            
            // Subtract the two offsets
            results = {
                top:  offset.top  - parentOffset.top,
                left: offset.left - parentOffset.left
            };
        }
        
        return results;
    },
    
    offsetParent: function() {
        var offsetParent = this[0].offsetParent;
        while ( offsetParent && (!/^body|html$/i.test(offsetParent.tagName) && $.css(offsetParent, 'position') == 'static') )
            offsetParent = offsetParent.offsetParent;
        return $(offsetParent);
    }
});

function num(el, prop) {
    return parseInt($.curCSS(el.jquery?el[0]:el,prop,true))||0;
};

})(jQuery);

jQuery.fn.highlightFade = function(settings) {
    var o = (settings && settings.constructor == String) ? {start: settings} : settings || {};
    var d = jQuery.highlightFade.defaults;
    var i = o['interval'] || d['interval'];
    var a = o['attr'] || d['attr'];
    var ts = {
        'linear': function(s,e,t,c) { return parseInt(s+(c/t)*(e-s)); },
        'sinusoidal': function(s,e,t,c) { return parseInt(s+Math.sin(((c/t)*90)*(Math.PI/180))*(e-s)); },
        'exponential': function(s,e,t,c) { return parseInt(s+(Math.pow(c/t,2))*(e-s)); }
    };
    var t = (o['iterator'] && o['iterator'].constructor == Function) ? o['iterator'] : ts[o['iterator']] || ts[d['iterator']] || ts['linear'];
    if (d['iterator'] && d['iterator'].constructor == Function) t = d['iterator'];
    return this.each(function() {
        if (!this.highlighting) this.highlighting = {};
        var e = (this.highlighting[a]) ? this.highlighting[a].end : jQuery.highlightFade.getBaseValue(this,a) || [255,255,255];
        var c = jQuery.highlightFade.getRGB(o['start'] || o['colour'] || o['color'] || d['start'] || [255,255,128]);
        var s = jQuery.speed(o['speed'] || d['speed']);
        var r = o['final'] || (this.highlighting[a] && this.highlighting[a].orig) ? this.highlighting[a].orig : jQuery.curCSS(this,a);
        if (o['end'] || d['end']) r = jQuery.highlightFade.asRGBString(e = jQuery.highlightFade.getRGB(o['end'] || d['end']));
        if (typeof o['final'] != 'undefined') r = o['final'];
        if (this.highlighting[a] && this.highlighting[a].timer) window.clearInterval(this.highlighting[a].timer);
        this.highlighting[a] = { steps: ((s.duration) / i), interval: i, currentStep: 0, start: c, end: e, orig: r, attr: a };
        jQuery.highlightFade(this,a,o['complete'],t);
    });
};

jQuery.highlightFade = function(e,a,o,t) {
    e.highlighting[a].timer = window.setInterval(function() { 
        var newR = t(e.highlighting[a].start[0],e.highlighting[a].end[0],e.highlighting[a].steps,e.highlighting[a].currentStep);
        var newG = t(e.highlighting[a].start[1],e.highlighting[a].end[1],e.highlighting[a].steps,e.highlighting[a].currentStep);
        var newB = t(e.highlighting[a].start[2],e.highlighting[a].end[2],e.highlighting[a].steps,e.highlighting[a].currentStep);
        jQuery(e).css(a,jQuery.highlightFade.asRGBString([newR,newG,newB]));
        if (e.highlighting[a].currentStep++ >= e.highlighting[a].steps) {
            jQuery(e).css(a,e.highlighting[a].orig || '');
            window.clearInterval(e.highlighting[a].timer);
            e.highlighting[a] = null;
            if (o && o.constructor == Function) o.call(e);
        }
    },e.highlighting[a].interval);
};

jQuery.highlightFade.defaults = {
    start: [255,255,128],
    interval: 50,
    speed: 400,
    attr: 'backgroundColor'
};

jQuery.highlightFade.getRGB = function(c,d) {
    var result;
    if (c && c.constructor == Array && c.length == 3) return c;
    if (result = /rgb\(\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*,\s*([0-9]{1,3})\s*\)/.exec(c))
        return [parseInt(result[1]),parseInt(result[2]),parseInt(result[3])];
    else if (result = /rgb\(\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*,\s*([0-9]+(?:\.[0-9]+)?)\%\s*\)/.exec(c))
        return [parseFloat(result[1])*2.55,parseFloat(result[2])*2.55,parseFloat(result[3])*2.55];
    else if (result = /#([a-fA-F0-9]{2})([a-fA-F0-9]{2})([a-fA-F0-9]{2})/.exec(c))
        return [parseInt("0x" + result[1]),parseInt("0x" + result[2]),parseInt("0x" + result[3])];
    else if (result = /#([a-fA-F0-9])([a-fA-F0-9])([a-fA-F0-9])/.exec(c))
        return [parseInt("0x"+ result[1] + result[1]),parseInt("0x" + result[2] + result[2]),parseInt("0x" + result[3] + result[3])];
    else
        return jQuery.highlightFade.checkColorName(c) || d || null;
};

jQuery.highlightFade.asRGBString = function(a) {
    return "rgb(" + a.join(",") + ")";
};

jQuery.highlightFade.getBaseValue = function(e,a,b) {
    var s, t;
    b = b || false;
    t = a = a || jQuery.highlightFade.defaults['attr'];
    do {
        s = jQuery(e).css(t || 'backgroundColor');
        if ((s  != '' && s != 'transparent') || (e.tagName.toLowerCase() == "body") || (!b && e.highlighting && e.highlighting[a] && e.highlighting[a].end)) break; 
        t = false;
    } while (e = e.parentNode);
    if (!b && e.highlighting && e.highlighting[a] && e.highlighting[a].end) s = e.highlighting[a].end;
    if (s == undefined || s == '' || s == 'transparent') s = [255,255,255];
    return jQuery.highlightFade.getRGB(s);
};

jQuery.highlightFade.checkColorName = function(c) {
    if (!c) return null;
    switch(c.replace(/^\s*|\s*$/g,'').toLowerCase()) {
        case 'aqua': return [0,255,255];
        case 'black': return [0,0,0];
        case 'blue': return [0,0,255];
        case 'fuchsia': return [255,0,255];
        case 'gray': return [128,128,128];
        case 'green': return [0,128,0];
        case 'lime': return [0,255,0];
        case 'maroon': return [128,0,0];
        case 'navy': return [0,0,128];
        case 'olive': return [128,128,0];
        case 'purple': return [128,0,128];
        case 'red': return [255,0,0];
        case 'silver': return [192,192,192];
        case 'teal': return [0,128,128];
        case 'white': return [255,255,255];
        case 'yellow': return [255,255,0];
    }
};

/*
 * jQuery Tooltip plugin 1.3
 *
 * http://bassistance.de/jquery-plugins/jquery-plugin-tooltip/
 * http://docs.jquery.com/Plugins/Tooltip
 *
 * Copyright (c) 2006 - 2008 Jörn Zaefferer
 *
 * $Id: jquery.tooltip.js 5741 2008-06-21 15:22:16Z joern.zaefferer $
 * 
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
 
;(function($) {
    
        // the tooltip element
    var helper = {},
        // the current tooltipped element
        current,
        // the title of the current element, used for restoring
        title,
        // timeout id for delayed tooltips
        tID,
        // IE 5.5 or 6
        IE = $.browser.msie && /MSIE\s(5\.5|6\.)/.test(navigator.userAgent),
        // flag for mouse tracking
        track = false;
    
    $.tooltip = {
        blocked: false,
        defaults: {
            delay: 200,
            fade: false,
            showURL: true,
            extraClass: "",
            top: 15,
            left: 15,
            id: "tooltip"
        },
        block: function() {
            $.tooltip.blocked = !$.tooltip.blocked;
        }
    };
    
    $.fn.extend({
        tooltip: function(settings) {
            settings = $.extend({}, $.tooltip.defaults, settings);
            createHelper(settings);
            return this.each(function() {
                    $.data(this, "tooltip", settings);
                    this.tOpacity = helper.parent.css("opacity");
                    // copy tooltip into its own expando and remove the title
                    this.tooltipText = this.title;
                    $(this).removeAttr("title");
                    // also remove alt attribute to prevent default tooltip in IE
                    this.alt = "";
                })
                .mouseover(save)
                .mouseout(hide)
                .click(hide);
        },
        fixPNG: IE ? function() {
            return this.each(function () {
                var image = $(this).css('backgroundImage');
                if (image.match(/^url\(["']?(.*\.png)["']?\)$/i)) {
                    image = RegExp.$1;
                    $(this).css({
                        'backgroundImage': 'none',
                        'filter': "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=crop, src='" + image + "')"
                    }).each(function () {
                        var position = $(this).css('position');
                        if (position != 'absolute' && position != 'relative')
                            $(this).css('position', 'relative');
                    });
                }
            });
        } : function() { return this; },
        unfixPNG: IE ? function() {
            return this.each(function () {
                $(this).css({'filter': '', backgroundImage: ''});
            });
        } : function() { return this; },
        hideWhenEmpty: function() {
            return this.each(function() {
                $(this)[ $(this).html() ? "show" : "hide" ]();
            });
        },
        url: function() {
            return this.attr('href') || this.attr('src');
        }
    });
    
    function createHelper(settings) {
        // there can be only one tooltip helper
        if( helper.parent )
            return;
        // create the helper, h3 for title, div for url
        helper.parent = $('<div id="' + settings.id + '"><h3></h3><div class="body"></div><div class="url"></div></div>')
            // add to document
            .appendTo(document.body)
            // hide it at first
            .hide();
            
        // apply bgiframe if available
        if ( $.fn.bgiframe )
            helper.parent.bgiframe();
        
        // save references to title and url elements
        helper.title = $('h3', helper.parent);
        helper.body = $('div.body', helper.parent);
        helper.url = $('div.url', helper.parent);
    }
    
    function settings(element) {
        return $.data(element, "tooltip");
    }
    
    // main event handler to start showing tooltips
    function handle(event) {
        // show helper, either with timeout or on instant
        if( settings(this).delay )
            tID = setTimeout(show, settings(this).delay);
        else
            show();
        
        // if selected, update the helper position when the mouse moves
        track = !!settings(this).track;
        $(document.body).bind('mousemove', update);
            
        // update at least once
        update(event);
    }
    
    // save elements title before the tooltip is displayed
    function save() {
        // if this is the current source, or it has no title (occurs with click event), stop
        if ( $.tooltip.blocked || this == current || (!this.tooltipText && !settings(this).bodyHandler) )
            return;

        // save current
        current = this;
        title = this.tooltipText;
        
        if ( settings(this).bodyHandler ) {
            helper.title.hide();
            var bodyContent = settings(this).bodyHandler.call(this);
            if (bodyContent.nodeType || bodyContent.jquery) {
                helper.body.empty().append(bodyContent)
            } else {
                helper.body.html( bodyContent );
            }
            helper.body.show();
        } else if ( settings(this).showBody ) {
            var parts = title.split(settings(this).showBody);
            helper.title.html(parts.shift()).show();
            helper.body.empty();
            for(var i = 0, part; (part = parts[i]); i++) {
                if(i > 0)
                    helper.body.append("<br/>");
                helper.body.append(part);
            }
            helper.body.hideWhenEmpty();
        } else {
            helper.title.html(title).show();
            helper.body.hide();
        }
        
        // if element has href or src, add and show it, otherwise hide it
        if( settings(this).showURL && $(this).url() )
            helper.url.html( $(this).url().replace('http://', '') ).show();
        else 
            helper.url.hide();
        
        // add an optional class for this tip
        helper.parent.addClass(settings(this).extraClass);

        // fix PNG background for IE
        if (settings(this).fixPNG )
            helper.parent.fixPNG();
            
        handle.apply(this, arguments);
    }
    
    // delete timeout and show helper
    function show() {
        tID = null;
        if ((!IE || !$.fn.bgiframe) && settings(current).fade) {
            if (helper.parent.is(":animated"))
                helper.parent.stop().show().fadeTo(settings(current).fade, current.tOpacity);
            else
                helper.parent.is(':visible') ? helper.parent.fadeTo(settings(current).fade, current.tOpacity) : helper.parent.fadeIn(settings(current).fade);
        } else {
            helper.parent.show();
        }
        update();
    }
    
    /**
     * callback for mousemove
     * updates the helper position
     * removes itself when no current element
     */
    function update(event)  {
        if($.tooltip.blocked)
            return;
        
        if (event && event.target.tagName == "OPTION") {
            return;
        }
        
        // stop updating when tracking is disabled and the tooltip is visible
        if ( !track && helper.parent.is(":visible")) {
            $(document.body).unbind('mousemove', update)
        }
        
        // if no current element is available, remove this listener
        if( current == null ) {
            $(document.body).unbind('mousemove', update);
            return; 
        }
        
        // remove position helper classes
        helper.parent.removeClass("viewport-right").removeClass("viewport-bottom");
        
        var left = helper.parent[0].offsetLeft;
        var top = helper.parent[0].offsetTop;
        if (event) {
            // position the helper 15 pixel to bottom right, starting from mouse position
            left = event.pageX + settings(current).left;
            top = event.pageY + settings(current).top;
            var right='auto';
            if (settings(current).positionLeft) {
                right = $(window).width() - left;
                left = 'auto';
            }
            helper.parent.css({
                left: left,
                right: right,
                top: top
            });
        }
        
        var v = viewport(),
            h = helper.parent[0];
        // check horizontal position
        if (v.x + v.cx < h.offsetLeft + h.offsetWidth) {
            left -= h.offsetWidth + 20 + settings(current).left;
            helper.parent.css({left: left + 'px'}).addClass("viewport-right");
        }
        // check vertical position
        if (v.y + v.cy < h.offsetTop + h.offsetHeight) {
            top -= h.offsetHeight + 20 + settings(current).top;
            helper.parent.css({top: top + 'px'}).addClass("viewport-bottom");
        }
    }
    
    function viewport() {
        return {
            x: $(window).scrollLeft(),
            y: $(window).scrollTop(),
            cx: $(window).width(),
            cy: $(window).height()
        };
    }
    
    // hide helper and restore added classes and the title
    function hide(event) {
        if($.tooltip.blocked)
            return;
        // clear timeout if possible
        if(tID)
            clearTimeout(tID);
        // no more current element
        current = null;
        
        var tsettings = settings(this);
        function complete() {
            helper.parent.removeClass( tsettings.extraClass ).hide().css("opacity", "");
        }
        if ((!IE || !$.fn.bgiframe) && tsettings.fade) {
            if (helper.parent.is(':animated'))
                helper.parent.stop().fadeTo(tsettings.fade, 0, complete);
            else
                helper.parent.stop().fadeOut(tsettings.fade, complete);
        } else
            complete();
        
        if( settings(this).fixPNG )
            helper.parent.unfixPNG();
    }
    
})(jQuery);

/**
 * jQuery-Plugin "clearField"
 * 
 * @version: 1.1, 04.12.2010
 * 
 * @author: Stijn Van Minnebruggen
 *          stijn@donotfold.be
 *          http://www.donotfold.be
 * 
 * @example: $('selector').clearField();
 * @example: $('selector').clearField({ blurClass: 'myBlurredClass', activeClass: 'myActiveClass' });
 * 
 */

(function($) {
    
    $.fn.clearField = function(settings) {
        
        /**
         * Settings
         * 
         */
        
        settings = jQuery.extend({
            blurClass: 'clearFieldBlurred',
            activeClass: 'clearFieldActive',
            attribute: 'rel',
            value: ''
        }, settings);
        
        
        /**
         * loop each element
         * 
         */
        
        return $(this).each(function() {
            
            /**
             * Set element
             * 
             */
            
            var el = $(this);
            
            
            /**
             * Get starting value
             * 
             */
            
            settings.value = el.val();
            
            
            /**
             * Add or get attribute
             * 
             */
            
            if(el.attr(settings.attribute) == undefined) {
                el.attr(settings.attribute, el.val()).addClass(settings.blurClass);
            } else {
                settings.value = el.attr(settings.attribute);
            }
            
            
            /**
             * Set focus action
             * 
             */
            
            el.focus(function() {
                
                if(el.val() == el.attr(settings.attribute)) {
                    el.val('').removeClass(settings.blurClass).addClass(settings.activeClass);
                }
                
            });
            
            
            /**
             * Set blur action
             * 
             */
            
            el.blur(function() {
                
                if(el.val() == '') {
                    el.val(el.attr(settings.attribute)).removeClass(settings.activeClass).addClass(settings.blurClass);
                }
                
            });
            
            
        });
        
    };
    
})(jQuery);
