
console.log('script.js loaded');

window.addEventListener('load', function(e) {
	console.log('Document loaded');
	init();
});


function init() {
	getPosts();
	addDisplay();
}

function getPosts() {
	let posts;
	console.log('In the init()');
	let xhr = new XMLHttpRequest();

	xhr.open('GET', 'api/posts/', true);

	xhr.onreadystatechange = function() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200) {
				posts = JSON.parse(xhr.responseText);
				console.log(posts);
				postToDiv(posts);
			} else {
				console.error('Error: ' + xhr.status);
			}
		}
	};

	xhr.send(); // Don't forget to send the request
}


function postToDiv(posts) {
	let postDiv = document.getElementById('postListDiv');
	postDiv.textContent = '';

	for (let i = 0; i < posts.length; i++) {

		let userDiv = document.createElement('div');
		userDiv.id = '';
		userDiv.textContent = posts[i].pageUser.name;
		postDiv.appendChild(userDiv);

		let postTitleHeader = document.createElement('h4');
		postTitleHeader.id = 'postTitleHeader';
		postTitleHeader.textContent = posts[i].title;
		postDiv.appendChild(postTitleHeader);

		let postDescription = document.createElement('p');
		postDescription.id = 'postDescription';
		postDescription.textContent = posts[i].description;
		postDiv.appendChild(postDescription);

		if (posts[i].imageUrl !== null) {
			let image = document.createElement('img');
			image.id = 'imageUrl';
			image.src = posts[i].imageUrl;
			image.style = "width:400px;height:300px";
			postDiv.appendChild(image);
		}
		postDiv.appendChild(document.createElement('hr'));
	}
}

function addDisplay() {
	document.displayForm.disp.addEventListener('click', function(event) {
		event.preventDefault();
		let newPostDisplay = document.getElementById('newPostDisplay');
		newPostDisplay.textContent = '';

		let title = document.createElement('input');
		title.type = 'text';
		title.name = 'newPostTitle';
		title.placeholder = 'enter title here';

		newPostDisplay.appendChild(title);
		newPostDisplay.appendChild(document.createElement('br'));

		let description = document.createElement('input');
		description.type = 'textarea';
		description.name = 'newPostDescription';
		description.placeholder = 'enter description here';

		newPostDisplay.appendChild(description);
		newPostDisplay.appendChild(document.createElement('br'));

		let newImage = document.createElement('input');
		newImage.type = 'text';
		newImage.name = 'newImageUrl';
		newImage.placeholder = 'paste image Url';

		newPostDisplay.appendChild(newImage);
		newPostDisplay.appendChild(document.createElement('br'));

		let displayPostSubmit = document.createElement('button');
		displayPostSubmit.name = 'addPostButton';
		displayPostSubmit.class = 'btn btn-dark';
		displayPostSubmit.textContent = 'submit';

		newPostDisplay.appendChild(displayPostSubmit);


	});
}

