window.parentList = [];

$(document).ready(function() {
	//$('#container select').on('change', (function() {
	$('#categories').change(function() {
		
		var category = $('#categories').val();
		var parentCategory = categoryToParentMap[category];
		
		/* Cleanup, Member to be created reselected */		
		//Reset parents list
		parentList = [];
		
		// Hide all the category drop downs
		$.each(categoryToParentMap, function(category, parentCategory) {
			$("#" + category).hide();
		});
		
		// Empty the old form to create new member
		$('#newMemberFields').empty();
		$('#newMemberFormFieldSet').hide();
		$('#result').hide();

		
		//If member of root category is being created, directly provide the form to create new member 
		if (jQuery.isEmptyObject(parentCategory)) {
			populateNewMemberFields(categoryToHtmlLabels, category);
			
		} else {
			parentList.push(category);
			// Push hierarchical parents of selected category on a stack
			while(!jQuery.isEmptyObject(parentCategory)) {
				parentList.push(parentCategory);
				parentCategory = categoryToParentMap[parentCategory];
			}
			getMembersForCategory(parentList.pop());
		}
	});
	
	//Generic function to track change event on all the category drop downs
	$("select[id$='List']").change(function() {
		var category = parentList.pop();
		
		// If last category, provide form to create new member
		if(jQuery.isEmptyObject(parentList)) {
			populateNewMemberFields(categoryToHtmlLabels, category);
		} else {
			getMembersForCategory(category);
		}
	});
	
	/* attach a submit handler to the form */
    $("#newMemberForm").submit(function(event) {

        /* stop form from submitting normally */
        event.preventDefault();

        /* get some values from elements on the page: */
        var $form = $(this);
        var url = $form.attr('action');
        
        var posting = $.post(url, $("#newMemberForm").serialize());

        /* Put the results in a div */
        posting.done(function(data) {
            $("#result").empty().append(data + " using servlet " + url);
            $('#result').show();
        });
    });
	
});

// Get members for given category using ajax
function getMembersForCategory(selectedCategory) {
	$.ajax({
		url : 'FetchMembers',
		data : {
			category : selectedCategory
		},
		success : function(responseText) {
			$('#' + selectedCategory + 'List').html(responseText);
		}
	});
	$("#" + selectedCategory).show();
}

// Populate the form for creating new member using the labels for selected category
function populateNewMemberFields (categoryToHtmlLabels, selectedCategory) {
	$.each(categoryToHtmlLabels, function(category, labels) {
		if(category === selectedCategory) {
			$.each(labels, function(index, label) {
				$('#newMemberFields').append(label + ": <input type='text' name='" + label + "'><br/><br/>");
			})
			$("#newMemberLegend").html('<strong>New ' + selectedCategory + ' form</strong>');
			$("#addMember").html('Add ' + selectedCategory);
			$('#newMemberFormFieldSet').show();
			return false;
		}
	});
	
}