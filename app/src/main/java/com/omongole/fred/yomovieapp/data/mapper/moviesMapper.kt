package com.omongole.fred.yomovieapp.data.mapper

import com.omongole.fred.yomovieapp.data.model.movies.MovieDetailDTO
import com.omongole.fred.yomovieapp.data.model.movies.MovieDto
import com.omongole.fred.yomovieapp.data.model.valueObjects.CompanyDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.CountryDTO
import com.omongole.fred.yomovieapp.data.model.valueObjects.GenreDTO
import com.omongole.fred.yomovieapp.domain.model.movies.Movie
import com.omongole.fred.yomovieapp.domain.model.movies.MovieDetail
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Company
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Country
import com.omongole.fred.yomovieapp.domain.model.valueObjects.Genre

fun MovieDto.toMovie() : Movie {
    return Movie(
        id = id,
        imagePath = backdrop_path,
        title = title ?: "",
        posterPath = poster_path,
        releaseDate = release_date ?: "",
        rating = vote_average ?: 0.toDouble()
    )
}

fun MovieDetailDTO.toMovieDetail() : MovieDetail {
    return MovieDetail(
        id = id,
        budget = budget ?: 0,
        genres = genres.map { it.toGenre() },
        language = original_language ?: "",
        title = title ?: "",
        overview = overview ?: "",
        posterPath = poster_path ?: "",
        backdropPath = backdrop_path ?: "",
        productionCompanies = production_companies.map { it.toCompany() },
        productionCountries = production_countries.map { it.toCountry() },
        releaseDate = release_date,
        revenue = revenue ?: 0,
        duration = runtime ?: "",
        status = status ?: "",
        tagline = tagline ?: "",
        rating = vote_average ?: 0.toDouble()
    )
}

fun GenreDTO.toGenre() : Genre {
    return Genre(
        id = id,
        name = name ?: ""
    )
}

fun CompanyDTO.toCompany() : Company {
    return Company(
        id = id,
        logoPath = logo_path ?: "",
        name = name ?: ""
    )
}

fun CountryDTO.toCountry() : Country {
    return Country(
        name = name ?: ""
    )
}