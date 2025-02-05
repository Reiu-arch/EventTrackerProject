
console.log('script.js loaded');

window.addEventListener('load', function(e) {
	console.log('Document loaded');
	init();
});


function init() {
	getPosts();
	add();
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

function add() {
	document.displayForm.disp.addEventListener('click', function(event) {
		event.preventDefault();
	//add
		let newPostDisplay = document.getElementById('newPostDisplay');
	//mod
		newPostDisplay.textContent = ''; 
		//add
		let title = document.createElement('input');
		//mod
		title.type = 'text';
		title.name = 'newPostTitle';
		title.placeholder = 'enter title here';
		//append
		newPostDisplay.appendChild(title);
		newPostDisplay.appendChild(document.createElement('br'));
		//add
		let description = document.createElement('input');
		description.type = 'textarea';
		description.name = 'newPostDescription';
		description.placeholder = 'enter description here';
		newPostDisplay.appendChild(description);
		newPostDisplay.appendChild(document.createElement('br'));
		//mod
		let newImage = document.createElement('input');
		newImage.type = 'text';
		newImage.name = 'newImageUrl';
		newImage.placeholder = 'paste image Url';
		newPostDisplay.appendChild(newImage);
		newPostDisplay.appendChild(document.createElement('br'));
		//append
		let displayPostSubmit = document.createElement('button');
		displayPostSubmit.name = 'addPostButton';
		displayPostSubmit.classList.add('btn', 'btn-dark'); // Correct className usage
		displayPostSubmit.textContent = 'submit';
	//append
		newPostDisplay.appendChild(displayPostSubmit);

		//added a new function to grab the info right after hitting submit
		displayPostSubmit.addEventListener('click', function(event) {
			event.preventDefault();

			let title = newPostDisplay.querySelector('[name="newPostTitle"]').value;
			let description = newPostDisplay.querySelector('[name="newPostDescription"]').value;
			let imageUrl = newPostDisplay.querySelector('[name="newImageUrl"]').value;

			console.log('title: ' + title + ' description: ' + description + ' imageUrl: ' + imageUrl);

			let xhr = new XMLHttpRequest();
			
			let post = {
							'title': title,
							'description': description,
							'imageUrl': imageUrl
							
						};
						xhr.open('POST', 'api/posts', true);
									xhr.setRequestHeader('Content-Type', 'application/json');

									xhr.onreadystatechange = function() {
										if (xhr.readyState === 4) {
											if (xhr.status === 201) {
												console.log('New Film Added');
											} else {
												console.error('Error: ' + xhr.status);
											}
										}
									};
									xhr.send(JSON.stringify(post));
				
		});
	});
}

