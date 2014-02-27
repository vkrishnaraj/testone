
CKEDITOR.plugins.add('margins',
{
	requires : ['richcombo'],
	init : function( editor )
	{
		//  array of strings to choose from that'll be inserted into the editor
		var sizeChoices = [];
		sizeChoices.push(['0.5', '0.5 Inches', '0.5 Inches']);
		sizeChoices.push(['0.75', '0.75 Inches', '0.75 Inches']);
		sizeChoices.push(['1.0', '1.0 Inches', '1.0 Inches']);
		sizeChoices.push(['1.25', '1.25 Inches', '1.25 Inches']);
		sizeChoices.push(['1.5', '1.5 Inches', '1.5 Inches']);
		sizeChoices.push(['1.75', '1.75 Inches', '1.75 Inches']);
		sizeChoices.push(['2.0', '2.0 Inches', '2.0 Inches']);
		
		var hiddenMarginT = document.getElementById('hiddenCKMarginT');
		var hiddenMarginB = document.getElementById('hiddenCKMarginB');
		var hiddenMarginL = document.getElementById('hiddenCKMarginL');
		var hiddenMarginR = document.getElementById('hiddenCKMarginR');
		
		var labelMarginT = 'Top';
		var labelMarginB = 'Bottom';
		var labelMarginL = 'Left';
		var labelMarginR = 'Right';
		
		if (hiddenMarginT) {
			labelMarginT = hiddenMarginT.value;
		}
		if (hiddenMarginB) {
			labelMarginB = hiddenMarginB.value;
		}
		if (hiddenMarginL) {
			labelMarginL = hiddenMarginL.value;
		}
		if (hiddenMarginR) {
			labelMarginR = hiddenMarginR.value;
		}

		// add the menu to the editor
		editor.ui.addRichCombo('MarginTop',
		{
			label: 		labelMarginT,
			title: 		'Margin Top',
			voiceLabel: 'Top',
			className: 	'cke_format',
			multiSelect:false,
			panel:
			{
				css: [ editor.config.contentsCss, CKEDITOR.skin.getPath('editor') ],
				voiceLabel: editor.lang.panelVoiceLabel
			},

			init: function()
			{
				this.startGroup( "Margin Top" );
				for (var i in sizeChoices)
				{
					this.add(sizeChoices[i][0], sizeChoices[i][1], sizeChoices[i][2]);
				}
			},

			onClick: function( value )
			{
				var hiddenMargin = document.getElementById('hiddenCKMarginT');
				if (hiddenMargin) {
					hiddenMargin.value = value;
					this.setValue(value);
				}
			}
		});

		// add the menu to the editor
		editor.ui.addRichCombo('MarginBottom',
		{
			label: 		labelMarginB,
			title: 		'Margin Bottom',
			voiceLabel: 'Bottom',
			className: 	'cke_format',
			multiSelect:false,
			panel:
			{
				css: [ editor.config.contentsCss, CKEDITOR.skin.getPath('editor') ],
				voiceLabel: editor.lang.panelVoiceLabel
			},

			init: function()
			{
				this.startGroup( "Margin Bottom" );
				for (var i in sizeChoices)
				{
					this.add(sizeChoices[i][0], sizeChoices[i][1], sizeChoices[i][2]);
				}
			},

			onClick: function( value )
			{
				var hiddenMargin = document.getElementById('hiddenCKMarginB');
				if (hiddenMargin) {
					hiddenMargin.value = value;
					this.setValue(value);
				}
			}
		});

		// add the menu to the editor
		editor.ui.addRichCombo('MarginLeft',
		{
			label: 		labelMarginL,
			title: 		'Margin Left',
			voiceLabel: 'Left',
			className: 	'cke_format',
			multiSelect:false,
			panel:
			{
				css: [ editor.config.contentsCss, CKEDITOR.skin.getPath('editor') ],
				voiceLabel: editor.lang.panelVoiceLabel
			},

			init: function()
			{
				this.startGroup( "Margin Left" );
				for (var i in sizeChoices)
				{
					this.add(sizeChoices[i][0], sizeChoices[i][1], sizeChoices[i][2]);
				}
			},

			onClick: function( value )
			{
				var hiddenMargin = document.getElementById('hiddenCKMarginL');
				if (hiddenMargin) {
					hiddenMargin.value = value;
					this.setValue(value);
				}
			}
		});

		// add the menu to the editor
		editor.ui.addRichCombo('MarginRight',
		{
			label: 		labelMarginR,
			title: 		'Margin Right',
			voiceLabel: 'Right',
			className: 	'cke_format',
			multiSelect:false,
			panel:
			{
				css: [ editor.config.contentsCss, CKEDITOR.skin.getPath('editor') ],
				voiceLabel: editor.lang.panelVoiceLabel
			},

			init: function()
			{
				this.startGroup( "Margin Right" );
				for (var i in sizeChoices)
				{
					this.add(sizeChoices[i][0], sizeChoices[i][1], sizeChoices[i][2]);
				}
			},

			onClick: function( value )
			{
				var hiddenMargin = document.getElementById('hiddenCKMarginR');
				if (hiddenMargin) {
					hiddenMargin.value = value;
					this.setValue(value);
				}
			}
		});
	}
});