<script lang="ts">
	import '$lib/styles/global.css';
	import { page } from '$app/stores';
	import { scaleMode } from '$lib/stores/scaleMode';
	import { onMount } from 'svelte';
	import TableOfContent from '$lib/components/TableOfContent.svelte';
	import SizeMode from '$lib/components/SizeMode.svelte';
	import { browser } from '$app/environment';
	import Copyright from '$lib/components/Copyright.svelte';

	let container: any;

	let isScaled = false;

	if (browser) {
		// Subscribe to the store to get the current scale mode value
		scaleMode.subscribe(value => {
			isScaled = value;

			if (isScaled) {
				adjustSize();
				window.addEventListener('resize', adjustSize);
			} else {
				window.removeEventListener('resize', adjustSize);
				if (container) {
					container.style.width  = '1280px';
					container.style.height =  '720px';
				}
			}
		});
	}

	function adjustSize() {
		if (!container) return;
		const aspectRatio = 1280 / 720;
		const windowWidth = window.innerWidth;
		const windowHeight = window.innerHeight;
		const windowRatio = windowWidth / windowHeight;

		if (windowRatio > aspectRatio) {
			container.style.height = '100vh';
			container.style.width = `${100 * aspectRatio}vh`;
		} else {
			container.style.width = '100vw';
			container.style.height = `${100 / aspectRatio}vw`;
		}
	}
</script>

<div class="container" class:scale-mode={isScaled} bind:this={container}>
	<div class="content">
		<TableOfContent />
		<SizeMode />
		<slot />
		<Copyright />
	</div>
</div>

<style>
	.container {
		width: 1280px;
		height: 720px;
		margin: 0 auto;
		display: flex;
		border: 5px solid #ccc; /* Optional: adds a border to visualize the container */
		position: relative;
	}
	.container.scale-mode {
		width: 100%;
		height: 100%;
		max-width: 100vw;
		max-height: 100vh;
	}
	.content {
		width: 100%;
		height: calc(100% - 20px);
		overflow: auto;
		padding: 10px;
		margin: 0 auto;
		display: flex;
		justify-content: center;
		align-items: center;
		color: #C0F1FF;
		background: #112c63;
		font-family: 'Noto Sans', 'Cormorant Garamond', serif;
		transition: all 0.3s ease;
	}
</style>
