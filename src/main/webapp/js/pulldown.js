$(function(){

	// 《selectタグ その①》路線を選択すると呼び出される関数
	$('select[name="parentCategory"]').change(function(){

	  // 《selectタグ その①》nameの値を取得
	  var parent = $('select[name="parentCategory"] option:selected').attr("value");
	  console.log(parent);
	
	　// 《selectタグ その②》 子要素タグの個数を数え上げ
	  var count = $('select[name="childCategory"]').children().length;
	  console.log(count);
	
	  for(a=0; a<count; a++){
		  var child = $('select[name="childCategory"] option:eq('+a+')');
		  
	　　　　　// 《selectタグ その①》で選択した路線と等しいname値を持つoptionタグを表示
	      if(child.attr("value") === parent){
	    	  child.show();
	      } else {
	    	  child.hide();
	      }
	  }
	});	  
	  
	$('select[name="childCategory"]').change(function(){

	  // 《selectタグ その①》nameの値を取得
//	  $('select[name="childCategory"]').attr("value", "id");
	  var child = $('select[name="childCategory"] option:selected').attr("class");
	  console.log(child);
	
	　// 《selectタグ その②》 子要素タグの個数を数え上げ
	  var count = $('select[name="grandchildCategory"]').children().length;
	  console.log(count);
	
	  // 《selectタグ その③》 子要素タグの個数を数え上げ
	  for(b=0; b<count; b++){
	
		  var grandchild = $('select[name="grandchildCategory"] option:eq('+b+')');
	
	　　// 《selectタグ その②》で選択した駅と等しいname値を持つoptionタグを表示
		  if(grandchild.attr("value") === child){
			  grandchild.show();
		  } else {
			  grandchild.hide();
		  }
	  }

	});

});