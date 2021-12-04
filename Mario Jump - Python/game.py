#William Farris
#A small game in python of Mario doing his own thing

import pygame
import time

from pygame.locals import*
from time import sleep

class Sprite():
	def __init__(self, x, y, width, height, im):
		self.x = x
		self.y = y
		self.w = width
		self.h = height
		self.image = pygame.image.load(im)
	def collision(self, a):
		if self.x + self.w < a.x:
			return 0
		if self.x > a.x + a.w:
			return 0
		if self.y + self.h < a.y:
			return 0
		if self.y > a.y + a.h:
			return 0
		return 1
	def isTube(self):
		return 0
	def isMario(self):
		return 0
	def isGoomba(self):
		return 0
	def isFireball(self):
		return 0

class Mario(Sprite):
	def __init__(self, x, y):
		super(Mario, self).__init__(x, y, 55, 95, "mario1.png")
		self.vert_velocity = 12.0
		self.moveOffset = x
		self.marioJumped = 0 #starts false
		self.marioImageNum = 0
		self.px = 0
		self.py = 0
		self.marioImage1 = pygame.image.load("mario1.png")
		self.marioImage2 = pygame.image.load("mario2.png")
		self.marioImage3 = pygame.image.load("mario3.png")
		self.marioImage4 = pygame.image.load("mario4.png")
		self.marioImage5 = pygame.image.load("mario5.png")
		self.marioSprites = [self.marioImage1, self.marioImage2, self.marioImage3, self.marioImage4, self.marioImage5]

	def isMario(self):
		return 1

	def update(self):
		self.vert_velocity = self.vert_velocity + 2.9
		self.y = self.y + self.vert_velocity
		if self.y > 400 - self.h:
			self.vert_velocity = 0.0
			self.y = 400 - self.h;
		if self.vert_velocity == 0.0:
			self.marioJumped = 0
		self.image = self.marioSprites[self.marioImageNum]

	def jump(self):
		if self.marioJumped == 0:
			self.vert_velocity = self.vert_velocity - 7.5
		if self.vert_velocity < -28:
			self.marioJumped = 1

	def prevPos(self):
		self.px = self.moveOffset
		self.py = self.y

	def tubeReject(self, t):
		if self.moveOffset + self.w >= t.start and self.px + self.w <= t.start:
			self.moveOffset = t.start - self.w
		if self.moveOffset <= t.start + t.w and self.px >= t.start + t.w:
			self.moveOffset = t.start + t.w
		if self.y + self.h >= t.y and self.py + self.h <= t.y:
			self.marioJumped = 0
			self.vert_velocity = 0.0
			self.y = t.y - self.h
		if self.y >= t.y + t.h and self.py <= t.y + t.h:
			self.y = t.y + t.h

class Tube(Sprite):
	def __init__(self, x, y, m):
		super(Tube, self).__init__(x, y, 55, 400, "tube.png")
		self.model = m
		self.start = x
	def update(self):
		self.x = self.start - self.model.mario.moveOffset + 200;
	def isTube(self):
		return 1

class Goomba(Sprite):
	def __init__(self, x, y, m):
		super(Goomba, self).__init__(x, y, 50, 60, "goomba.png")
		self.model = m
		self.start = x
		self.vert_velocity = 0
		self.goombaTouchedRight = 0
		self.goombaBounce = 10
		self.burnGoomba = 0
		self.goombaImage = pygame.image.load("goomba_fire.png")
		self.framesOnFire = 40

	def update(self):
		self.x = self.start - self.model.mario.moveOffset + 200
		for sprite in self.model.sprites:
			self.s = sprite
			if self.s.isFireball() == 1:
				if self.collision(self.s) == 1:
					self.burnGoomba = 1
					self.model.sprites.remove(sprite)

			if self.burnGoomba == 0:
				for sprite in self.model.sprites:
					self.s = sprite
					if self.s.isTube():
						if self.collision(self.s) == 1:
							self.goombaTouchedRight = abs(self.goombaTouchedRight - 1)
							self.start += self.goombaBounce
							self.goombaBounce = -self.goombaBounce
							print(self.goombaBounce)
				if self.goombaTouchedRight == 0:
					self.start = self.start - 1
				if self.goombaTouchedRight == 1:
					self.start = self.start + 1

			if self.burnGoomba == 1:
				self.image = self.goombaImage
				for sprite in self.model.sprites:
					self.s = sprite
					if self.s.isGoomba() == 1:
						if self.framesOnFire > 0:
							self.framesOnFire -= 1
						else:
							self.model.sprites.remove(sprite)

			self.vert_velocity += 2.9
			self.y += self.vert_velocity
			if self.y > 400 - self.h:
				self.vert_velocity = 0.0
				self.y = 400 - self.h
	def isGoomba(self):
		return 1

class Fireball(Sprite):
	def __init__ (self, x, y, m):
		super(Fireball, self).__init__(x, y, 25, 25, "fireball.png")
		self.model = m
		self.start = x
		self.vert_velocity = 0

	def update(self):
		self.x = self.start - self.model.mario.moveOffset + 200
		self.start = self.start + 13
		self.vert_velocity += 2.9
		self.y += self.vert_velocity
		if self.y > 400 - self.h:
			self.y = 400 - self.h
			self.vert_velocity = -16.8

	def isFireball(self):
		return 1
		

class Model():
	def __init__(self):
		#self.dest_x = 0
		#self.dest_y = 0
		self.mario = Mario(200,50)
		self.sprites = []
		self.sprites.append(self.mario)
		self.sprites.append(Tube(375, 240, self))
		self.sprites.append(Tube(528, 257, self))
		self.sprites.append(Tube(687, 272, self))
		self.sprites.append(Tube(969, 237, self))
		self.sprites.append(Tube(1182, 338, self))
		self.sprites.append(Goomba(800, 300, self))

	def update(self):
		for sprite in self.sprites:
			sprite.update()
		for sprite in self.sprites:
			self.s = sprite
			if self.s.isTube() == 1:
				if self.mario.collision(self.s) == 1:
					self.mario.tubeReject(sprite)

class View():
	def __init__(self, model):
		screen_size = (800,500)
		self.screen = pygame.display.set_mode(screen_size, 32)
		self.model = model

	def update(self):    
		self.screen.fill([128, 255, 255])
		pygame.draw.line(self.screen, (89, 89, 89), (0, 400), (800, 400), 1)
		for sprite in self.model.sprites:
			self.screen.blit(sprite.image, (sprite.x, sprite.y))
		pygame.display.flip()

class Controller():
	def __init__(self, model):
		self.model = model
		self.keep_going = True
		self.noHold = 1

	def update(self):
		self.model.mario.prevPos()
		for event in pygame.event.get():
			if event.type == QUIT:
				self.keep_going = False
			elif event.type == KEYDOWN:
				if event.key == K_ESCAPE:
					self.keep_going = False
		keys = pygame.key.get_pressed()
		if keys[K_LEFT]:
			self.model.mario.moveOffset -= 10
			self.model.mario.marioImageNum -= 1
			if self.model.mario.marioImageNum < 0:
				self.model.mario.marioImageNum = 4
		if keys[K_RIGHT]:
			self.model.mario.moveOffset += 10
			self.model.mario.marioImageNum += 1
			if self.model.mario.marioImageNum > 4:
				self.model.mario.marioImageNum = 0
		if keys[K_UP]:
			self.model.mario.jump()
		else:
			self.model.mario.marioJumped = 1;

		if keys[K_LCTRL]:
			if self.noHold == 1:
				self.noHold = 0
				self.model.sprites.append(Fireball(self.model.mario.moveOffset, self.model.mario.y + (self.model.mario.h / 2), self.model))
				
		else:
			self.noHold = 1

print("Use the arrow keys to move and left Ctrl to throw fireballs. Press Esc to quit.")
pygame.init()
m = Model()
v = View(m)
c = Controller(m)
while c.keep_going:
	c.update()
	m.update()
	v.update()
	sleep(0.02)
print("Goodbye")