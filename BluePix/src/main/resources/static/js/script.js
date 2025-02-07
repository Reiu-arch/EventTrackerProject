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
	console.log('Fetching posts...');
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

	xhr.send();
}

function postToDiv(posts) {
	let postDiv = document.getElementById('postListDiv');
	postDiv.textContent = '';

	for (let i = 0; i < posts.length; i++) {
		let postContainer = document.createElement('div');
		postContainer.classList.add('post-container', 'mb-4', 'p-3', 'border', 'rounded', 'shadow-sm');

		let userDiv = document.createElement('div');
		userDiv.textContent = posts[i].pageUser.name;
		postContainer.appendChild(userDiv);

		let postTitleHeader = document.createElement('h4');
		postTitleHeader.textContent = posts[i].title;
		postContainer.appendChild(postTitleHeader);

		let viewButton = document.createElement('button');
		viewButton.classList.add('btn', 'btn-info', 'mt-2');
		viewButton.textContent = 'View';
		postContainer.appendChild(viewButton);

		let postDescription = document.createElement('p');
		postDescription.textContent = posts[i].description;
		postContainer.appendChild(postDescription);

		if (posts[i].imageUrl) {
			let image = document.createElement('img');
			image.src = posts[i].imageUrl;
			image.style = "width:400px;height:300px";
			postContainer.appendChild(image);
		}

		let editForm = document.createElement('form');
		editForm.classList.add('mt-3', 'p-3', 'border', 'rounded', 'd-none');

		let titleInput = document.createElement('input');
		titleInput.type = 'text';
		titleInput.value = posts[i].title;
		titleInput.classList.add('form-control', 'mb-2');

		let descInput = document.createElement('textarea');
		descInput.value = posts[i].description;
		descInput.classList.add('form-control', 'mb-2');

		let imageInput = document.createElement('input');
		imageInput.type = 'text';
		imageInput.value = posts[i].imageUrl;
		imageInput.classList.add('form-control', 'mb-2');

		let saveButton = document.createElement('button');
		saveButton.textContent = 'Edit';
		saveButton.classList.add('btn', 'btn-warning', 'me-2');

		let deleteButton = document.createElement('button');
		deleteButton.textContent = 'Delete';
		deleteButton.classList.add('btn', 'btn-danger');

		editForm.appendChild(titleInput);
		editForm.appendChild(descInput);
		editForm.appendChild(imageInput);
		editForm.appendChild(saveButton);
		editForm.appendChild(deleteButton);

		postContainer.appendChild(editForm);

		viewButton.addEventListener('click', function() {
			editForm.classList.toggle('d-none');
		});

		saveButton.addEventListener('click', function(event) {
			event.preventDefault();
			let updatedPost = {
				title: titleInput.value,
				description: descInput.value,
				imageUrl: imageInput.value
			};

			let xhr = new XMLHttpRequest();
			xhr.open('PUT', `api/posts/${posts[i].id}`, true);
			xhr.setRequestHeader('Content-Type', 'application/json');

			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4) {
					if (xhr.status === 200) {
						console.log('Post updated successfully');
						getPosts();
					} else {
						console.error('Error updating post:', xhr.status);
					}
				}
			};
			xhr.send(JSON.stringify(updatedPost));
		});

		deleteButton.addEventListener('click', function(event) {
			event.preventDefault();
			let xhr = new XMLHttpRequest();
			xhr.open('DELETE', `api/posts/${posts[i].id}`, true);

			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4) {
					if (xhr.status === 204) {
						console.log('Post deleted successfully');
						getPosts();
					} else {
						console.error('Error deleting post:', xhr.status);
					}
				}
			};
			xhr.send();
		});

		postDiv.appendChild(postContainer);
	}
}

function add() {
	document.displayForm.disp.addEventListener('click', function(event) {
		event.preventDefault();

		let newPostDisplay = document.getElementById('newPostDisplay');
		newPostDisplay.textContent = '';

		let title = document.createElement('input');
		title.type = 'text';
		title.name = 'newPostTitle';
		title.placeholder = 'Enter title here';
		newPostDisplay.appendChild(title);
		newPostDisplay.appendChild(document.createElement('br'));

		let description = document.createElement('input');
		description.type = 'textarea';
		description.name = 'newPostDescription';
		description.placeholder = 'Enter description here';
		newPostDisplay.appendChild(description);
		newPostDisplay.appendChild(document.createElement('br'));

		let newImage = document.createElement('input');
		newImage.type = 'text';
		newImage.name = 'newImageUrl';
		newImage.placeholder = 'Paste image URL';
		newPostDisplay.appendChild(newImage);
		newPostDisplay.appendChild(document.createElement('br'));

		let displayPostSubmit = document.createElement('button');
		displayPostSubmit.name = 'addPostButton';
		displayPostSubmit.classList.add('btn', 'btn-dark');
		displayPostSubmit.textContent = 'Submit';
		newPostDisplay.appendChild(displayPostSubmit);

		displayPostSubmit.addEventListener('click', function(event) {
			event.preventDefault();

			let title = newPostDisplay.querySelector('[name="newPostTitle"]').value;
			let description = newPostDisplay.querySelector('[name="newPostDescription"]').value;
			let imageUrl = newPostDisplay.querySelector('[name="newImageUrl"]').value;

			console.log('title: ' + title + ' description: ' + description + ' imageUrl: ' + imageUrl);

			let xhr = new XMLHttpRequest();
			let post = { title, description, imageUrl };

			xhr.open('POST', 'api/posts', true);
			xhr.setRequestHeader('Content-Type', 'application/json');

			xhr.onreadystatechange = function() {
				if (xhr.readyState === 4) {
					if (xhr.status === 201) {
						console.log('New Post Added');
						getPosts();
					} else {
						console.error('Error: ' + xhr.status);
					}
				}
			};
			xhr.send(JSON.stringify(post));
		});
	});
}
