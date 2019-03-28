
$(function() {

    $.validator.setDefaults( {
        onsubmit:true,
        onfocusout:false,
        onkeyup:false,
        errorElement: "em",
        ignore: "",
        errorPlacement: function ( error, element ) {
            console.log(error)
            // Add the `help-block` class to the error element
           // error.addClass( "help-block" );

            // Add `has-feedback` class to the parent div.form-group
            // in order to add icons to inputs
            console.log( element.prop( "type"))
            element.parent( "div" ).addClass( "has-feedback" );

            if ( element.prop( "type" ) === "checkbox" ) {
                //error.insertAfter( element.parent( "label" ) );
            } else {
                //error.insertAfter( element );
            }

            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !element.next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-remove form-control-feedback'></span>" ).insertAfter( element );
            }

        },
        //单条校验失败，后会调用此方法，在此方法中，编写错误消息如何显示 及  校验失败回调方法
        showErrors : function(errorMap, errorList) {
            var errorMsg = []
            for(var error in errorMap) {
                errorMsg.push(errorMap[error]);
            }
            if(errorMsg.length>0){
                layerHelp.formErrorPrompt.show(errorMsg);
            }

            // 此处注意，一定要调用默认方法，这样保证提示消息的默认效果
            this.defaultShowErrors();
        },
        success: function ( label, element ) {
            // Add the span element, if doesn't exists, and apply the icon classes to it.
            if ( !$( element ).next( "span" )[ 0 ] ) {
                $( "<span class='glyphicon glyphicon-ok form-control-feedback'></span>" ).insertAfter( $( element ) );
            }
        },
        highlight: function ( element, errorClass, validClass ) {
            $( element ).parent( "div" ).addClass( "has-error" ).removeClass( "has-success" );
            $( element ).next( "span" ).addClass( "glyphicon-remove" ).removeClass( "glyphicon-ok" );

        },
        unhighlight: function ( element, errorClass, validClass ) {
            $( element ).parent( "div" ).addClass( "has-success" ).removeClass( "has-error" );
            $( element ).next( "span" ).addClass( "glyphicon-ok" ).removeClass( "glyphicon-remove" );
        }
    } );


});


