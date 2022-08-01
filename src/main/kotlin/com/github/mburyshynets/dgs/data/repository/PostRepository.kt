package com.github.mburyshynets.dgs.data.repository

import com.github.mburyshynets.dgs.data.model.Post
import org.springframework.data.repository.CrudRepository

interface PostRepository : CrudRepository<Post, Long>
